package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x; // worldx worldy is the top corner of sprite
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y; // worldx worldy is the top corner of sprite
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2; // will store the 2 possible tiles that will cause a collision

        switch(entity.direction) {
        case "up":
            entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize; // adds the pixels to the player to
            // predict if collision if this direction happens
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow]; // could be the same exact tile as tileNum1
            if (gp.tileM.tiles[tileNum1].collision || gp.tileM.tiles[tileNum2].collision) {
                entity.collisionOn = true;
            }
            break;
        case "down":
            entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize; // adds the pixels to the player to
            // predict if collision if this direction happens
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow]; // could be the same exact tile as tileNum1
            if (gp.tileM.tiles[tileNum1].collision || gp.tileM.tiles[tileNum2].collision) {
                entity.collisionOn = true;
            }
            break;
        case "left":
            entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize; // adds the pixels to the player to predict
            // if collision if this direction happens
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow]; // could be the same exact tile as tileNum1
            if (gp.tileM.tiles[tileNum1].collision || gp.tileM.tiles[tileNum2].collision) {
                entity.collisionOn = true;
            }
            break;
        case "right":
            entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize; // adds the pixels to the player to
            // predict if collision if this direction happens
            tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow]; // could be the same exact tile as tileNum1
            if (gp.tileM.tiles[tileNum1].collision || gp.tileM.tiles[tileNum2].collision) {
                entity.collisionOn = true;
            }
            break;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;
        for (int i  = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                //get entity solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y  = entity.worldY + entity.solidArea.y;

                // get object solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch (entity.direction) {
                case "up":
                    entity.solidArea.y -= entity.speed;
                    if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                        if (gp.obj[i].collision) {
                            entity.collisionOn = true;
                        }
                        if (player) {
                            index = i;
                        }
                    }
                    break;
                case "down":
                    entity.solidArea.y += entity.speed;
                    if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                        if (gp.obj[i].collision) {
                            entity.collisionOn = true;
                        }
                        if (player) {
                            index = i;
                        }
                    }
                    break;
                case "left":
                    entity.solidArea.x -= entity.speed;
                    if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                        if (gp.obj[i].collision) {
                            entity.collisionOn = true;
                        }
                        if (player) {
                            index = i;
                        }
                    }
                    break;
                case "right":
                    entity.solidArea.x += entity.speed;
                    if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                        if (gp.obj[i].collision) {
                            entity.collisionOn = true;
                        }
                        if (player) {
                            index = i;
                        }
                    }
                    break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }
        return index; // check if hitting any object and if is return the index accordingly to process reaction
    }
}
