package at.osim.listdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;

public class OutlinedLineDecorationView extends LineDecorationView {

    private Paint outlinePaint;
    private Paint erasePaint;

    private int outlineSpace;
    private int outlineWidth;

    public OutlinedLineDecorationView(Context context) {
        super(context);
    }

    public OutlinedLineDecorationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OutlinedLineDecorationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);

        outlineSpace = (int) dpToPx(8);
        outlineWidth = (int) dpToPx(12);

        outlinePaint = new Paint(linePaint);
        outlinePaint.setStrokeWidth(linePaint.getStrokeWidth() + outlineWidth);
        erasePaint = new Paint();
        erasePaint.setAntiAlias(true);
        erasePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        erasePaint.setStrokeWidth(linePaint.getStrokeWidth() + outlineSpace);
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.saveLayer(null, null);

        drawOuterCircle(canvas);
        drawOuterLine(canvas);

        cutOutLine(canvas);
        cutOutCircle(canvas);

        super.draw(canvas);

        canvas.restore();
    }


    protected void drawOuterCircle(Canvas canvas) {
        canvas.drawCircle(center.x, center.y, radius + outlineWidth / 2, outlinePaint);
    }

    protected void drawOuterLine(Canvas canvas) {
        if (isStart) {
            if (orientation == Orientation.VERTICAL) {
                canvas.drawLine(center.x, center.y, center.x, height, outlinePaint);
            } else {
                canvas.drawLine(center.x, center.y, width, center.y, outlinePaint);
            }
        } else if (isEnd) {
            if (orientation == Orientation.VERTICAL) {
                canvas.drawLine(center.x, 0, center.x, center.y, outlinePaint);
            } else {
                canvas.drawLine(0, center.y, center.x, center.y, outlinePaint);
            }
        } else {
            if (orientation == Orientation.VERTICAL) {
                canvas.drawLine(center.x, 0, center.x, height, outlinePaint);
            } else {
                canvas.drawLine(0, center.y, width, center.y, outlinePaint);
            }
        }
    }

    protected void cutOutLine(Canvas canvas) {
        if (isStart) {
            if (orientation == Orientation.VERTICAL) {
                canvas.drawLine(center.x, center.y, center.x, height, erasePaint);
            } else {
                canvas.drawLine(center.x, center.y, width, center.y, erasePaint);
            }
        } else if (isEnd) {
            if (orientation == Orientation.VERTICAL) {
                canvas.drawLine(center.x, 0, center.x, center.y, erasePaint);
            } else {
                canvas.drawLine(0, center.y, center.x, center.y, erasePaint);
            }
        } else {
            if (orientation == Orientation.VERTICAL) {
                canvas.drawLine(center.x, 0, center.x, height, erasePaint);
            } else {
                canvas.drawLine(0, center.y, width, center.y, erasePaint);
            }
        }
    }

    protected void cutOutCircle(Canvas canvas) {
        canvas.drawCircle(center.x, center.y, radius + outlineSpace / 2, erasePaint);
    }
}
