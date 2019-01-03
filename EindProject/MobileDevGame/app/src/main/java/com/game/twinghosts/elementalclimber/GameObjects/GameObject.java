package com.game.twinghosts.elementalclimber.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.game.twinghosts.elementalclimber.Data.GameData;
import com.game.twinghosts.elementalclimber.Utility.Vector2;

public class GameObject {
    protected Bitmap image;
    protected Vector2 size;

    public Rect collisionRectangle;
    public Vector2 position;

    public GameObject(Bitmap bmp) {
        image = bmp;
        position = new Vector2(0, 0);
        size = new Vector2(bmp.getWidth(), bmp.getHeight());
        setCollisionRectangle();
    }

    public GameObject(Bitmap bmp, float x, float y) {
        image = bmp;
        position = new Vector2(x, y);
        size = new Vector2(bmp.getWidth(), bmp.getHeight());
        setCollisionRectangle();
    }

    public GameObject(Bitmap bmp, Vector2 position) {
        image = bmp;
        this.position = position;
        size = new Vector2(bmp.getWidth(), bmp.getHeight());
        setCollisionRectangle();
    }

    public void resizeImage(Vector2 newSize) {
        image = Bitmap.createScaledBitmap(image, (int)newSize.x, (int)newSize.y, false);
        size = newSize;
        setCollisionRectangle();
    }

    public void draw(Canvas canvas) {
        collisionRectangle.left = (int)position.x;
        collisionRectangle.right = (int)position.x + (int)GameData.BLOCK_SIZE;

        collisionRectangle.top = (int)position.y;
        collisionRectangle.bottom = (int)position.y + (int)GameData.BLOCK_SIZE;

        canvas.drawBitmap(image, position.x, position.y, null);
    }

    private void setCollisionRectangle(){
        collisionRectangle = new Rect();
        collisionRectangle.left = (int)position.x;
        collisionRectangle.right = (int)position.x + (int)GameData.BLOCK_SIZE;

        collisionRectangle.top = (int)position.y;
        collisionRectangle.bottom = (int)position.y + (int)GameData.BLOCK_SIZE;
    }

    public Bitmap getImage(){
        return image;
    }

    public Vector2 getSize(){
        return size;
    }
}
