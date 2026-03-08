package qlybaixe.login;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import qlybaixe.dao.UserDAO;
import qlybaixe.dashboard.Dashboard;
import qlybaixe.model.User;

/**
 * Màn hình đăng nhập – thiết kế 2 cột:
 *   Trái  : Banner gradient xanh đậm với icon + slogan
 *   Phải  : Form đăng nhập nền trắng, input bo tròn, nút hover animation
 *
 * Tương thích: Java 8 / Swing / NetBeans
 */
public class Login extends JFrame {

    // ── Màu sắc ──────────────────────────────────────────────────
    private static final Color PRIMARY      = new Color(13,  71, 161);
    private static final Color PRIMARY_DARK = new Color(8,   45, 105);
    private static final Color ACCENT       = new Color(21, 101, 192);
    private static final Color BG_FORM      = Color.WHITE;
    private static final Color BORDER_GRAY  = new Color(209, 213, 219);
    private static final Color TEXT_DARK    = new Color(30,  41,  59);
    private static final Color TEXT_FADED   = new Color(156, 163, 175);

    // ── Placeholder ───────────────────────────────────────────────
    private static final String PH_USER = "Nhập tên đăng nhập";
    private static final String PH_PASS = "Nhập mật khẩu";

    // ── Components ────────────────────────────────────────────────
    private JTextField     txtUsername;
    private JPasswordField txtPassword;
    private JButton        btnLogin;
    private JLabel         lblError;
    private char           defaultEcho;
    private UserDAO        userDAO;

    // ── Nút login đang hover? ─────────────────────────────────────
    private boolean btnHover = false;

    // ─────────────────────────────────────────────────────────────
    public Login() {
        if (!java.beans.Beans.isDesignTime()) {
            userDAO = new UserDAO();
        }
        initUI();
    }

    // ══════════════════════════════════════════════════════════════
    private void initUI() {
        setTitle("Quản Lý Sân Thể Thao – Đăng Nhập");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Root panel: 2 cột bằng nhau
        JPanel root = new JPanel(new GridLayout(1, 2, 0, 0));
        root.setPreferredSize(new Dimension(860, 540));
        setContentPane(root);

        root.add(buildBannerPanel());
        root.add(buildFormPanel());

        pack();
        setLocationRelativeTo(null);
    }

    // ──────────────────────────────────────────────────────────────
    //  BANNER PANEL – gradient xanh, tuyệt đối layout
    // ──────────────────────────────────────────────────────────────
    private JPanel buildBannerPanel() {

        JPanel banner = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                // Gradient dọc
                GradientPaint gp = new GradientPaint(
                        0, 0, PRIMARY,
                        0, getHeight(), PRIMARY_DARK);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                // Vòng trang trí mờ
                g2.setColor(new Color(255, 255, 255, 15));
                g2.fillOval(getWidth() - 160, getHeight() - 160, 280, 280);
                g2.setColor(new Color(255, 255, 255, 10));
                g2.fillOval(-70, -70, 240, 240);
                g2.dispose();
            }
        };

        int W = 430, H = 540;

        // Icon ⚽
        JLabel icon = new JLabel("\u26BD", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 72));
        icon.setForeground(Color.WHITE);
        icon.setBounds(0, 118, W, 90);
        banner.add(icon);

        // Tên hệ thống
        JLabel title = makeHtmlLabel(
                "<div style='text-align:center;line-height:1.5'>"
                + "<b>HỆ THỐNG</b><br>QUẢN LÝ SÂN<br>THỂ THAO</div>",
                new Font("Segoe UI", Font.PLAIN, 22), Color.WHITE);
        title.setBounds(20, 216, W - 40, 100);
        banner.add(title);

        // Dòng kẻ mờ
        JLabel sep = new JLabel();
        sep.setOpaque(true);
        sep.setBackground(new Color(255, 255, 255, 55));
        sep.setBounds(60, 330, W - 120, 1);
        banner.add(sep);

        // Slogan
        JLabel slogan = makeHtmlLabel(
                "<div style='text-align:center;line-height:1.7'>"
                + "Quản lý đặt sân nhanh chóng,<br>thuận tiện và chính xác.</div>",
                new Font("Segoe UI", Font.PLAIN, 13),
                new Color(197, 218, 255));
        slogan.setBounds(20, 344, W - 40, 70);
        banner.add(slogan);

        // Version tag
        JLabel ver = new JLabel("v2026.1", SwingConstants.CENTER);
        ver.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        ver.setForeground(new Color(160, 190, 230));
        ver.setBounds(0, H - 36, W, 20);
        banner.add(ver);

        banner.setPreferredSize(new Dimension(W, H));
        return banner;
    }

    // ──────────────────────────────────────────────────────────────
    //  FORM PANEL – nền trắng, absolute layout
    // ──────────────────────────────────────────────────────────────
    private JPanel buildFormPanel() {
        JPanel form = new JPanel(null);
        form.setBackground(BG_FORM);

        int cx = 48, w = 334, H = 540;

        // Chào mừng
        JLabel welcome = new JLabel("Chào mừng trở lại 👋");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        welcome.setForeground(TEXT_DARK);
        welcome.setBounds(cx, 90, w, 30);
        form.add(welcome);

        JLabel sub = new JLabel("Đăng nhập để sử dụng hệ thống");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sub.setForeground(TEXT_FADED);
        sub.setBounds(cx, 124, w, 18);
        form.add(sub);

        // Divider nhỏ
        JLabel divider = new JLabel();
        divider.setOpaque(true);
        divider.setBackground(new Color(226, 232, 240));
        divider.setBounds(cx, 154, w, 1);
        form.add(divider);

        // ── Tên đăng nhập ────────────────────────────────────────
        form.add(fieldLabel("Tên đăng nhập", cx, 172, w));

        txtUsername = new JTextField();
        styleField(txtUsername);
        txtUsername.setBounds(cx, 194, w, 42);
        form.add(txtUsername);

        // ── Mật khẩu ─────────────────────────────────────────────
        form.add(fieldLabel("Mật khẩu", cx, 252, w));

        txtPassword = new JPasswordField();
        styleField(txtPassword);
        txtPassword.setBounds(cx, 274, w, 42);
        form.add(txtPassword);

        // ── Thông báo lỗi ────────────────────────────────────────
        lblError = new JLabel(" ");
        lblError.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblError.setForeground(new Color(239, 68, 68));
        lblError.setBounds(cx, 322, w, 18);
        form.add(lblError);

        // ── Nút đăng nhập ────────────────────────────────────────
        btnLogin = buildLoginButton();
        btnLogin.setBounds(cx, 350, w, 44);
        form.add(btnLogin);

        // ── Gợi ý tài khoản mẫu ──────────────────────────────────
        JLabel hint = new JLabel("Tài khoản mẫu: admin / admin123", SwingConstants.CENTER);
        hint.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        hint.setForeground(TEXT_FADED);
        hint.setBounds(cx, 410, w, 16);
        form.add(hint);

        // ── Copyright ─────────────────────────────────────────────
        JLabel copy = new JLabel("© 2026 Sports Booking System", SwingConstants.CENTER);
        copy.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        copy.setForeground(new Color(203, 213, 225));
        copy.setBounds(cx, H - 30, w, 16);
        form.add(copy);

        initPlaceholders();
        getRootPane().setDefaultButton(btnLogin);
        form.setPreferredSize(new Dimension(430, H));
        return form;
    }

    // ──────────────────────────────────────────────────────────────
    private JLabel fieldLabel(String text, int x, int y, int w) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(TEXT_DARK);
        lbl.setBounds(x, y, w, 18);
        return lbl;
    }

    private JLabel makeHtmlLabel(String html, Font font, Color fg) {
        JLabel lbl = new JLabel("<html>" + html + "</html>", SwingConstants.CENTER);
        lbl.setFont(font);
        lbl.setForeground(fg);
        return lbl;
    }

    private void styleField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(TEXT_FADED);
        field.setBackground(BG_FORM);
        field.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(BORDER_GRAY, 10),
                BorderFactory.createEmptyBorder(8, 14, 8, 14)));
        field.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        new RoundedBorder(PRIMARY, 10),
                        BorderFactory.createEmptyBorder(8, 14, 8, 14)));
            }
            @Override public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        new RoundedBorder(BORDER_GRAY, 10),
                        BorderFactory.createEmptyBorder(8, 14, 8, 14)));
            }
        });
    }

    private JButton buildLoginButton() {
        JButton btn = new JButton("Đăng nhập") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(btnHover ? ACCENT : PRIMARY);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btnHover = true;  btn.repaint(); }
            @Override public void mouseExited(MouseEvent e)  { btnHover = false; btn.repaint(); }
        });
        btn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { handleLogin(); }
        });
        return btn;
    }

    // ──────────────────────────────────────────────────────────────
    //  Placeholder logic
    // ──────────────────────────────────────────────────────────────
    private void initPlaceholders() {
        defaultEcho = txtPassword.getEchoChar();
        setUserPH();
        setPassPH();

        txtUsername.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                if (PH_USER.equals(txtUsername.getText())) {
                    txtUsername.setText("");
                    txtUsername.setForeground(TEXT_DARK);
                }
            }
            @Override public void focusLost(FocusEvent e) {
                if (txtUsername.getText().trim().isEmpty()) setUserPH();
            }
        });

        txtPassword.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                if (PH_PASS.equals(new String(txtPassword.getPassword()))) {
                    txtPassword.setText("");
                    txtPassword.setForeground(TEXT_DARK);
                    txtPassword.setEchoChar(defaultEcho);
                }
            }
            @Override public void focusLost(FocusEvent e) {
                if (new String(txtPassword.getPassword()).isEmpty()) setPassPH();
            }
        });
    }

    private void setUserPH() {
        txtUsername.setForeground(TEXT_FADED);
        txtUsername.setText(PH_USER);
    }

    private void setPassPH() {
        txtPassword.setForeground(TEXT_FADED);
        txtPassword.setEchoChar((char) 0);
        txtPassword.setText(PH_PASS);
    }

    // ──────────────────────────────────────────────────────────────
    //  Input helpers
    // ──────────────────────────────────────────────────────────────
    private String getUser() {
        String v = txtUsername.getText().trim();
        return PH_USER.equals(v) ? "" : v;
    }

    private String getPass() {
        String v = new String(txtPassword.getPassword());
        return PH_PASS.equals(v) ? "" : v;
    }

    // ──────────────────────────────────────────────────────────────
    //  Xử lý đăng nhập (SwingWorker – không block EDT)
    // ──────────────────────────────────────────────────────────────
    private void handleLogin() {
        if (userDAO == null) userDAO = new UserDAO();

        final String username = getUser();
        final String password = getPass();

        if (username.isEmpty()) {
            showError("Vui lòng nhập tên đăng nhập.");
            txtUsername.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            showError("Vui lòng nhập mật khẩu.");
            txtPassword.requestFocus();
            return;
        }

        lblError.setText(" ");
        btnLogin.setEnabled(false);
        btnLogin.setText("Đang đăng nhập...");

        SwingWorker<User, Void> worker = new SwingWorker<User, Void>() {
            @Override
            protected User doInBackground() throws Exception {
                return userDAO.login(username, password);
            }
            @Override
            protected void done() {
                btnLogin.setEnabled(true);
                btnLogin.setText("Đăng nhập");
                try {
                    User user = get();
                    if (user == null) {
                        showError("Sai tên đăng nhập, mật khẩu hoặc tài khoản bị khóa.");
                        txtPassword.setText("");
                        setPassPH();
                        txtUsername.requestFocus();
                    } else {
                        Dashboard dashboard = new Dashboard();
                        dashboard.setVisible(true);
                        dispose();
                    }
                } catch (Exception ex) {
                    Throwable cause = (ex.getCause() != null) ? ex.getCause() : ex;
                    showError("Lỗi: " + cause.getMessage());
                }
            }
        };
        worker.execute();
    }

    private void showError(String msg) {
        lblError.setText(msg);
    }

    // ──────────────────────────────────────────────────────────────
    //  Inner class: border bo tròn cho các input
    // ──────────────────────────────────────────────────────────────
    private static class RoundedBorder extends AbstractBorder {
        private final Color color;
        private final int   radius;

        RoundedBorder(Color color, int radius) {
            this.color  = color;
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(2, 2, 2, 2);
        }
    }

    // ──────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() { new Login().setVisible(true); }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
