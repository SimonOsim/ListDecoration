package at.osim.listdecoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

public class LineDecorationView extends View {

    private enum Orientation {
        HORIZONTAL,
        VERTICAL
    }

    private enum Gravity {
        START,
        CENTER,
        END
    }

    private Paint textPaint;
    private Paint linePaint;

    private boolean isStart = false;
    private boolean isEnd = false;

    private int width;
    private int height;

    private boolean drawCircle = true;

    private String text = "";

    private Point center;
    private int radius;

    private Orientation orientation = Orientation.VERTICAL;
    private Gravity gravity = Gravity.CENTER;
    private int decoOffset;
    private int decoPadding;

    public LineDecorationView(Context context) {
        super(context);
        init(context, null);
    }

    public LineDecorationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LineDecorationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        textPaint = new Paint();
        linePaint = new Paint();

        if (attrs != null) {
            final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.LineDecorationView);
            isStart = attributes.getBoolean(R.styleable.LineDecorationView_deco_isStart, false);
            isEnd = attributes.getBoolean(R.styleable.LineDecorationView_deco_isEnd, false);
            linePaint.setColor(attributes.getColor(R.styleable.LineDecorationView_deco_line_color, R.attr.colorPrimary));
            textPaint.setColor(attributes.getColor(R.styleable.LineDecorationView_deco_text_color, Color.WHITE));
            textPaint.setTextSize(attributes.getDimensionPixelSize(R.styleable.LineDecorationView_deco_text_size, (int) dpToPx(12)));
            linePaint.setStrokeWidth(attributes.getDimensionPixelSize(R.styleable.LineDecorationView_deco_line_width, (int) dpToPx(2)));
            orientation = attributes.getInt(R.styleable.LineDecorationView_deco_orientation, 0) == 0 ? Orientation.VERTICAL : Orientation.HORIZONTAL;
            switch (attributes.getInt(R.styleable.LineDecorationView_deco_gravity, 0)) {
                case 1:
                    gravity = Gravity.START;
                    break;
                case 2:
                    gravity = Gravity.END;
                    break;
                default:
                    gravity = Gravity.CENTER;

            }
            decoOffset = attributes.getDimensionPixelSize(R.styleable.LineDecorationView_deco_offset, 0);
            decoPadding = attributes.getDimensionPixelSize(R.styleable.LineDecorationView_deco_padding, 0);

            attributes.recycle();
        }

        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
        linePaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawLine(canvas);
        drawCircle(canvas);
    }

    private void drawLine(Canvas canvas) {
        if (isStart) {
            if (orientation == Orientation.VERTICAL) {
                canvas.drawLine(center.x, center.y, center.x, height, linePaint);
            } else {
                canvas.drawLine(center.x, center.y, width, center.y, linePaint);
            }
        } else if (isEnd) {
            if (orientation == Orientation.VERTICAL) {
                canvas.drawLine(center.x, 0, center.x, center.y, linePaint);
            } else {
                canvas.drawLine(0, center.y, center.x, center.y, linePaint);
            }
        } else {
            if (orientation == Orientation.VERTICAL) {
                canvas.drawLine(center.x, 0, center.x, height, linePaint);
            } else {
                canvas.drawLine(0, center.y, width, center.y, linePaint);
            }
        }
    }

    private void drawCircle(Canvas canvas) {
        if (drawCircle) {
            canvas.drawCircle(center.x, center.y, radius, linePaint);
            canvas.drawText(text, center.x, center.y - ((textPaint.descent() + textPaint.ascent()) / 2), textPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.width = getMeasuredWidth();
        this.height = getMeasuredHeight();
        this.radius = Math.min(width, height) / 2 - (2 * decoPadding);
        calculateCenter();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public LineDecorationView setText(String text) {
        if (text == null) {
            this.text = "";
        } else {
            this.text = text;
        }
        return this;
    }

    protected float dpToPx(float dp) {
        return dp * this.getContext().getResources().getDisplayMetrics().density;
    }

    protected float pxToDp(float px) {
        return px / this.getContext().getResources().getDisplayMetrics().density;
    }

    public LineDecorationView isMiddle() {
        this.isStart = false;
        this.isEnd = false;
        return this;
    }

    public LineDecorationView isStart() {
        this.isStart = true;
        this.isEnd = false;
        drawCircle = true;
        return this;
    }

    public LineDecorationView isEnd() {
        this.isEnd = true;
        this.isStart = false;
        drawCircle = true;
        return this;
    }

    public LineDecorationView drawCircle(boolean draw) {
        this.drawCircle = draw;
        return this;
    }

    private void calculateCenter() {
        if (gravity == Gravity.CENTER) {
            center = new Point(width / 2, height / 2);
        } else if (gravity == Gravity.START) {
            if (orientation == Orientation.VERTICAL) {
                center = new Point(width / 2, decoOffset + radius);
            } else {
                center = new Point(decoOffset + radius, height / 2);
            }
        } else {
            if (orientation == Orientation.VERTICAL) {
                center = new Point(width / 2, height - decoOffset - radius);
            } else {
                center = new Point(width - decoOffset - radius, height / 2);
            }
        }
    }
}
