package qlybaixe.login;

import javax.swing.*;
import qlybaixe.dao.UserDAO;
import qlybaixe.dashboard.Dashboard;
import qlybaixe.model.User;

/**
 * Màn hình đăng nhập – chuẩn NetBeans JFrame Form
 */
public class Login extends javax.swing.JFrame {

    // ── Màu (dùng ngoài vùng GEN) ──────────────────────────────
    private static final java.awt.Color TEXT_DARK  = new java.awt.Color(30, 41, 59);
    private static final java.awt.Color TEXT_FADED = new java.awt.Color(156, 163, 175);
    private static final String PH_USER = "Nhập tên đăng nhập";
    private static final String PH_PASS = "Nhập mật khẩu";

    private UserDAO userDAO;
    private char    defaultEcho;

    // ── Constructor ─────────────────────────────────────────────
    public Login() {
        initComponents();
        if (!java.beans.Beans.isDesignTime()) {
            userDAO = new UserDAO();
        }
        setupAfterInit();
    }

    // ── Generated Code (NetBeans GUI Builder) ────────────────────
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBanner = new javax.swing.JPanel();
        lblIcon = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        lblSlogan = new javax.swing.JLabel();
        lblVersion = new javax.swing.JLabel();
        panelForm = new javax.swing.JPanel();
        lblWelcome = new javax.swing.JLabel();
        lblSub = new javax.swing.JLabel();
        lblUsernameField = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblPasswordField = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblError = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        lblHint = new javax.swing.JLabel();
        lblCopyright = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản Lý Sân Thể Thao – Đăng Nhập");
        setResizable(false);

        // ── panelBanner (trái – nền xanh) ───────────────────────
        panelBanner.setBackground(new java.awt.Color(13, 71, 161));

        lblIcon.setFont(new java.awt.Font("Segoe UI Emoji", 0, 72)); // NOI18N
        lblIcon.setForeground(new java.awt.Color(255, 255, 255));
        lblIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon.setText("⚽");

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("<html><div style='text-align:center;line-height:1.6'>HỆ THỐNG<br>QUẢN LÝ SÂN<br>THỂ THAO</div></html>");

        lblSlogan.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblSlogan.setForeground(new java.awt.Color(197, 218, 255));
        lblSlogan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlogan.setText("<html><div style='text-align:center;line-height:1.7'>Quản lý đặt sân nhanh chóng,<br>thuận tiện và chính xác.</div></html>");

        lblVersion.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblVersion.setForeground(new java.awt.Color(160, 190, 230));
        lblVersion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblVersion.setText("v2026.1");

        javax.swing.GroupLayout panelBannerLayout = new javax.swing.GroupLayout(panelBanner);
        panelBanner.setLayout(panelBannerLayout);
        panelBannerLayout.setHorizontalGroup(
            panelBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBannerLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(panelBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblIcon,    javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitle,   javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSlogan,  javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblVersion, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        panelBannerLayout.setVerticalGroup(
            panelBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBannerLayout.createSequentialGroup()
                .addContainerGap(118, Short.MAX_VALUE)
                .addComponent(lblIcon,   javax.swing.GroupLayout.PREFERRED_SIZE, 90,  javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(lblTitle,  javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(lblSlogan, javax.swing.GroupLayout.PREFERRED_SIZE, 60,  javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblVersion)
                .addGap(16, 16, 16))
        );

        // ── panelForm (phải – nền trắng) ────────────────────────
        panelForm.setBackground(new java.awt.Color(255, 255, 255));

        lblWelcome.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblWelcome.setForeground(new java.awt.Color(30, 41, 59));
        lblWelcome.setText("Chào mừng trở lại 👋");

        lblSub.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblSub.setForeground(new java.awt.Color(156, 163, 175));
        lblSub.setText("Đăng nhập để sử dụng hệ thống");

        lblUsernameField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUsernameField.setForeground(new java.awt.Color(30, 41, 59));
        lblUsernameField.setText("Tên đăng nhập");

        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtUsername.setForeground(new java.awt.Color(156, 163, 175));
        txtUsername.setText("Nhập tên đăng nhập");

        lblPasswordField.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPasswordField.setForeground(new java.awt.Color(30, 41, 59));
        lblPasswordField.setText("Mật khẩu");

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblError.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblError.setForeground(new java.awt.Color(239, 68, 68));
        lblError.setText(" ");

        btnLogin.setBackground(new java.awt.Color(13, 71, 161));
        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Đăng nhập");
        btnLogin.setBorderPainted(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        lblHint.setFont(new java.awt.Font("Segoe UI", 2, 11)); // NOI18N
        lblHint.setForeground(new java.awt.Color(156, 163, 175));
        lblHint.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHint.setText("Tài khoản mẫu: admin / admin123");

        lblCopyright.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        lblCopyright.setForeground(new java.awt.Color(203, 213, 225));
        lblCopyright.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCopyright.setText("© 2026 Sports Booking System");

        javax.swing.GroupLayout panelFormLayout = new javax.swing.GroupLayout(panelForm);
        panelForm.setLayout(panelFormLayout);
        panelFormLayout.setHorizontalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblWelcome,       javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                    .addComponent(lblSub,           javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                    .addComponent(lblUsernameField)
                    .addComponent(txtUsername,      javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                    .addComponent(lblPasswordField)
                    .addComponent(txtPassword,      javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                    .addComponent(lblError,         javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                    .addComponent(btnLogin,         javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                    .addComponent(lblHint,          javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                    .addComponent(lblCopyright,     javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))
                .addGap(48, 48, 48))
        );
        panelFormLayout.setVerticalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(lblWelcome)
                .addGap(6, 6, 6)
                .addComponent(lblSub)
                .addGap(28, 28, 28)
                .addComponent(lblUsernameField)
                .addGap(4, 4, 4)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(lblPasswordField)
                .addGap(4, 4, 4)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(lblError)
                .addGap(8, 8, 8)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblHint)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblCopyright)
                .addGap(14, 14, 14))
        );

        // ── Root layout ──────────────────────────────────────────
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelBanner, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(panelForm,   javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBanner, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(panelForm,   javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // ── Event Handler ────────────────────────────────────────────
    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        handleLogin();
    }//GEN-LAST:event_btnLoginActionPerformed

    // ── Setup sau initComponents ─────────────────────────────────
    private void setupAfterInit() {
        setLocationRelativeTo(null);
        setupPlaceholders();
        getRootPane().setDefaultButton(btnLogin);
    }

    private void setupPlaceholders() {
        defaultEcho = txtPassword.getEchoChar();
        setPassPlaceholder();

        txtUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override public void focusGained(java.awt.event.FocusEvent e) {
                if (PH_USER.equals(txtUsername.getText())) {
                    txtUsername.setText("");
                    txtUsername.setForeground(TEXT_DARK);
                }
            }
            @Override public void focusLost(java.awt.event.FocusEvent e) {
                if (txtUsername.getText().trim().isEmpty()) setUserPlaceholder();
            }
        });

        txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override public void focusGained(java.awt.event.FocusEvent e) {
                if (PH_PASS.equals(new String(txtPassword.getPassword()))) {
                    txtPassword.setText("");
                    txtPassword.setForeground(TEXT_DARK);
                    txtPassword.setEchoChar(defaultEcho);
                }
            }
            @Override public void focusLost(java.awt.event.FocusEvent e) {
                if (new String(txtPassword.getPassword()).isEmpty()) setPassPlaceholder();
            }
        });
    }

    private void setUserPlaceholder() {
        txtUsername.setForeground(TEXT_FADED);
        txtUsername.setText(PH_USER);
    }

    private void setPassPlaceholder() {
        txtPassword.setForeground(TEXT_FADED);
        txtPassword.setEchoChar((char) 0);
        txtPassword.setText(PH_PASS);
    }

    // ── Xử lý đăng nhập ─────────────────────────────────────────
    private void handleLogin() {
        if (userDAO == null) userDAO = new UserDAO();

        String username = PH_USER.equals(txtUsername.getText().trim()) ? "" : txtUsername.getText().trim();
        String password = PH_PASS.equals(new String(txtPassword.getPassword())) ? "" : new String(txtPassword.getPassword());

        if (username.isEmpty()) {
            lblError.setText("Vui lòng nhập tên đăng nhập.");
            txtUsername.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            lblError.setText("Vui lòng nhập mật khẩu.");
            txtPassword.requestFocus();
            return;
        }

        lblError.setText(" ");
        btnLogin.setEnabled(false);
        btnLogin.setText("Đang đăng nhập...");

        final String u = username, p = password;
        new javax.swing.SwingWorker<User, Void>() {
            @Override protected User doInBackground() throws Exception {
                return userDAO.login(u, p);
            }
            @Override protected void done() {
                btnLogin.setEnabled(true);
                btnLogin.setText("Đăng nhập");
                try {
                    User user = get();
                    if (user == null) {
                        lblError.setText("Sai tên đăng nhập hoặc mật khẩu.");
                        txtPassword.setText("");
                        setPassPlaceholder();
                        txtUsername.requestFocus();
                    } else {
                        new Dashboard(user).setVisible(true);
                        dispose();
                    }
                } catch (Exception ex) {
                    Throwable cause = ex.getCause() != null ? ex.getCause() : ex;
                    lblError.setText("Lỗi kết nối: " + cause.getMessage());
                }
            }
        }.execute();
    }

    // ── Main ────────────────────────────────────────────────────
    public static void main(String[] args) {
        try { javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel lblCopyright;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblHint;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblPasswordField;
    private javax.swing.JLabel lblSlogan;
    private javax.swing.JLabel lblSub;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUsernameField;
    private javax.swing.JLabel lblVersion;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JPanel panelBanner;
    private javax.swing.JPanel panelForm;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
