package com.game.twinghosts.elementalclimber.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.game.twinghosts.elementalclimber.Utility.Vector2;

public class GameObject {
    protected Bitmap image;
    protected Vector2 size;

    public Rect collisionRectangle;
    public Vector2 position;

    public GameObject(float x, float y, Vector2 size){
        this.size = size;
        position = new Vector2(x, y);
        setCollisionRectangle();
    }

    public GameObject(Bitmap bmp) {
        image = bmp;
        position = new Vector2(0, 0);
        size = new Vector2(bmp.getWidth(), bmp.getHeight());
        setCollisionRectangle();
    }

    public void draw(Canvas canvas) {
        setCollisionRectangle();

        if(image != null)
            canvas.drawBitmap(image, position.x, position.y, null);
    }

    public void setCollisionRectangle(){
        if(collisionRectangle == null)
            collisionRectangle = new Rect();

        collisionRectangle.left = (int)position.x - (int)size.x/2;
        collisionRectangle.right = (int)position.x + (int)size.x/2;

        collisionRectangle.top = (int)position.y - (int)size.y/2;
        collisionRectangle.bottom = (int)position.y + (int)size.y/2;
    }

    public Bitmap getImage(){
        return image;
    }

    public Vector2 getSize(){
        return size;
    }
}
