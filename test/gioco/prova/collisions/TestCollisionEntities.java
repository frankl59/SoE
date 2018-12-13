/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.collisions;

import gioco.prova.Game;
import gioco.prova.Handler;
import gioco.prova.bullets.Arrow;
import gioco.prova.bullets.Fireball;
import gioco.prova.bullets.Kunai;
import gioco.prova.entities.ControllerEntities;
import gioco.prova.entities.Enemies;
import gioco.prova.entities.Enemy1;
import gioco.prova.entities.Enemy2;
import gioco.prova.entities.Enemy3;
import gioco.prova.entities.Player;
import gioco.prova.entities.Ramen;
import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Chris
 */
public class TestCollisionEntities {

    private Game game;
    private Handler handler;
    private Enemy1 enemy1;
    private Enemy2 enemy2;
    private Enemy3 enemy3;
    private Ramen ramen;
    private Player player;
    private ControllerEntities controller;

    public TestCollisionEntities() {
        game = Game.getGameIstance();
        handler = Handler.getHandlerInstance(game);
         }

    @Before
    public void setUp() {
        controller = new ControllerEntities(handler);
        enemy1 = new Enemy1(handler, game.getWidth(), 400, controller);
        enemy1.setX(enemy1.getX() + enemy1.getWidth());
        enemy2 = new Enemy2(handler, game.getWidth(), 400);
        enemy2.setX(enemy2.getX() + enemy2.getWidth());
        enemy3 = new Enemy3(handler, game.getWidth(), 400, controller);
        enemy3.setX(enemy3.getX() + enemy3.getWidth());
        
        controller.addEnemy(enemy1);
        controller.addEnemy(enemy2);
        controller.addEnemy(enemy3);
        //player = new Player(handler, 100, 325, controller);
        player = Player.getPlayerInstance(handler, 100, 400, controller);
        
    }

    @After
    public void tearDown() {
        enemy1 = null;
        enemy2 = null;
        enemy3 = null;
        Player.restartPlayer();
    }

    @Test
    public void testCollisionPlayerEnemy() {
        assertFalse(checkEnemyCollision(0, 0));
        enemy2.setX(150);
        assertTrue(checkEnemyCollision(0, 0));
        enemy2.setX(30);
        assertFalse(checkEnemyCollision(0, 0));
        enemy2.setX(31);
        assertTrue(checkEnemyCollision(0, 0));
        enemy2.setX(214);
        assertTrue(checkEnemyCollision(0, 0));
        enemy2.setX(215);
        assertFalse(checkEnemyCollision(0, 0));

        enemy2.setX(400);
        assertFalse(checkEnemyCollision(0, 0));

        enemy3.setX(100);
        assertTrue(checkEnemyCollision(0, 0));
        enemy3.setX(41);
        assertTrue(checkEnemyCollision(0, 0));
        enemy3.setX(40);
        assertFalse(checkEnemyCollision(0, 0));
        enemy2.setX(214);
        assertTrue(checkEnemyCollision(0, 0));
        enemy2.setX(215);
        assertFalse(checkEnemyCollision(0, 0));
    }

    @Test
    public void testKunaiPlayerEnemyCollision() {
        handler.getKeyManager().v = true;
        player.getInput();
        LinkedList<Kunai> listKunai = controller.getListKunaiPlayer();
        assertTrue(listKunai.size() == 1);
        Kunai kunai = null;
        for (Kunai k : controller.getListKunaiPlayer()) {
            kunai = k;
        }
        enemy2.setX(500);
        //System.out.println(enemy1.getX());
        while (!collisionKunaiPlayerEnemy(0, 0)) {
            kunai.tick();
            //System.out.println(kunai.getX());
        }
        assertFalse(collisionKunaiPlayerEnemy(0, 0));
        assertEquals(0, controller.getListKunaiPlayer().size());
        handler.getKeyManager().v = false;
    }

    @Test
    public void testKunaiEnemyPlayerCollision() {
        // Player vita non disponibile
        enemy3.setX(500);
        controller.addKunaiEnemies(new Kunai(handler, enemy3.getX(),
                enemy3.getY(), enemy3.getWidth(), enemy3.getHeight(), false));
        LinkedList<Kunai> listKunai = controller.getListKunaiEnemies();
        assertTrue(listKunai.size() == 1);
        Kunai kunai = null;
        for (Kunai k : controller.getListKunaiEnemies()) {
            kunai = k;
        }
        while (!collisionKunaiEnemyPlayer(0, 0)) {
            kunai.tick();
            //System.out.println(kunai.getX());
        }
        assertFalse(collisionKunaiEnemyPlayer(0, 0));
        assertEquals(0, controller.getListKunaiEnemies().size());

    }
    
    @Test
    public void testArrowEnemyPlayerCollision() {
        // Player vita non disponibile
        enemy1.setX(500);
        controller.addArrowEnemies(new Arrow(handler, enemy1.getX(),
                enemy1.getY(), enemy1.getWidth(), enemy1.getHeight(), false));
        LinkedList<Arrow> listArrow = controller.getListArrowEnemies();
        assertTrue(listArrow.size() == 1);
        Arrow arrow = null;
        for (Arrow a : controller.getListArrowEnemies()) {
            arrow = a;
        }
        while (!collisionArrowEnemyPlayer(0, 0)) {
            arrow.tick();
            //System.out.println(kunai.getX());
        }
        assertFalse(collisionArrowEnemyPlayer(0, 0));
        assertEquals(0, controller.getListArrowEnemies().size());

    }

    @Test
    public void testFireballPlayerEnemyCollision() {
        enemy3.setX(500);
        enemy2.setX(350);
        controller.addFireball(new Fireball(handler, player.getX(), player.getY(),
                player.getWidth(), player.getHeight()));
        LinkedList<Fireball> listFireball = controller.getF();
        assertTrue(listFireball.size() == 1);
        Fireball fireball = null;
        for (Fireball f : controller.getF()) {
            fireball = f;
        }
        while(!checkFireballCollisions(0, 0)){
            fireball.tick();
            //System.out.println(fireball.getX());
        }
        assertTrue(controller.getEnemies().isEmpty());
        //System.out.println(fireball.getX());
//        while(!listFireball.isEmpty()){
//            controller.tick();
//            System.out.println(fireball.getX());
//        }
//        assertTrue(listFireball.isEmpty());
        handler.getKeyManager().space = false;

    }
    
    @Test
    public void testPlayerRamenCollision(){
        player.setHealth(2);
        ramen = new Ramen(handler, handler.getWidth(), 550);
        controller.addRamen(ramen);
        assertTrue(controller.getListRamen().size()==1);
//        System.out.println(ramen.getY());
//        System.out.println(player.getY());
        while(!checkRamenPlayerCollision(0, 0)){
            ramen.tick();
            //System.out.println(ramen.getX());
        }
        assertTrue(controller.getListRamen().isEmpty());
        assertTrue(player.getHealth()==3);
    }

    private boolean checkEnemyCollision(float xOffset, float yOffset) {
        for (Enemies e : controller.getEnemies()) {
            if (e.getCollisionBounds(0f, 0f).intersects(player.getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
        return false;
    }
    
    private boolean checkRamenPlayerCollision(float xOffset, float yOffset) {
        for (Ramen ramen : controller.getListRamen()) {
            if (player.getHealth() != 3 && ramen.getCollisionBounds(0f, 0f).intersects(player.getCollisionBounds(xOffset, yOffset))) {
                player.setHealth(player.getHealth()+1);
                controller.removeRamen(ramen);
                return true;
            }
        }
        return false;
    }

    private boolean collisionKunaiPlayerEnemy(float xOffset, float yOffset) {
        for (Kunai k : controller.getListKunaiPlayer()) {
            if (k.getCollisionBounds(0f, 0f).intersects(enemy2.getCollisionBounds(xOffset, yOffset))) {
                controller.removeKunaiPlayer(k);
                return true;
            }
        }
        return false;
    }

    private boolean collisionKunaiEnemyPlayer(float xOffset, float yOffset) {
        for (Kunai k : controller.getListKunaiEnemies()) {
            if (k.getCollisionBounds(0f, 0f).intersects(player.getCollisionBounds(xOffset, yOffset))) {
                controller.removeKunaiEnemies(k);
                return true;
            }
        }
        return false;
    }
    
    private boolean collisionArrowEnemyPlayer(float xOffset, float yOffset) {
        for (Arrow a : controller.getListArrowEnemies()) {
            if (a.getCollisionBounds(0f, 0f).intersects(player.getCollisionBounds(xOffset, yOffset))) {
                controller.removeArrowEnemies(a);
                return true;
            }
        }
        return false;
    }

    private boolean checkFireballCollisions(float xOffset, float yOffset) {
        for (Fireball f : controller.getF()) {
            for (Enemies e : controller.getEnemies()) {
                if (e.getCollisionBounds(0f, 0f).intersects(f.getCollisionBounds(xOffset, yOffset))) {
                    controller.removeEnemy(e);
                    //System.out.println("Colpito");
                }
            }
        }
        if (controller.getEnemies().isEmpty()){
            return true;
        }
        return false;
    }
}
