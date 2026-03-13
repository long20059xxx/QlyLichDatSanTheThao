package qlybaixe.util;

import java.awt.*;
import javax.swing.JButton;

/**
 * Nút bấm có góc bo tròn (round corners).
 * Dùng thay thế JButton ở mọi màn hình.
 */
public class RoundedButton extends JButton {

    private int radius;
    private Color hoverBg;

    public RoundedButton(String text) {
        this(text, new Color(13, 71, 161), Color.WHITE, 10);
    }

    public RoundedButton(String text, Color bg, Color fg, int radius) {
        super(text);
        this.radius = radius;
        setBackground(bg);
        setForeground(fg);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // Hover color slightly lighter
        hoverBg = bg.brighter();
    }

    public void setRadius(int r) { this.radius = r; repaint(); }
    public void setHoverBg(Color c) { this.hoverBg = c; }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color bg = getModel().isPressed()  ? getBackground().darker()
                 : getModel().isRollover() ? hoverBg
                 : getBackground();

        g2.setColor(bg);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // no border
    }
}
