package qlybaixe.quanlynhanvien;

import java.awt.*;
import javax.swing.*;
import qlybaixe.dao.UserDAO;
import qlybaixe.dashboard.Dashboard;
import qlybaixe.model.User;
import qlybaixe.util.RoundedButton;

/**
 * JPanel – Form thêm nhân viên mới. Chuẩn NetBeans JPanel Form.
 */
public class PanelThemNV extends javax.swing.JPanel {

    private static final Color PRIMARY   = Dashboard.PRIMARY;
    private static final Color TEXT_DARK = Dashboard.TEXT_DARK;
    private static final Color TEXT_GRAY = Dashboard.TEXT_GRAY;
    private static final Color BORDER    = Dashboard.BORDER_CLR;

    private final UserDAO   userDAO = new UserDAO();
    private final User      currentUser;
    private final Dashboard dashboard;
    private Long editId;

    public PanelThemNV(User user, Dashboard dash) {
        this.currentUser = user;
        this.dashboard   = dash;
        initComponents();
    }
    public PanelThemNV() { this(null, null); }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollOuter  = new javax.swing.JScrollPane();
        panelOuter   = new javax.swing.JPanel();
        btnBack      = new javax.swing.JButton();
        lblTitle     = new javax.swing.JLabel();
        lblSubtitle  = new javax.swing.JLabel();
        panelForm    = new javax.swing.JPanel();
        lblFullName  = new javax.swing.JLabel();
        txtFullName  = new javax.swing.JTextField();
        lblUsername  = new javax.swing.JLabel();
        txtUsername  = new javax.swing.JTextField();
        lblPhone     = new javax.swing.JLabel();
        txtPhone     = new javax.swing.JTextField();
        lblEmail     = new javax.swing.JLabel();
        txtEmail     = new javax.swing.JTextField();
        lblPwd       = new javax.swing.JLabel();
        txtPwd       = new javax.swing.JPasswordField();
        lblPwdConf   = new javax.swing.JLabel();
        txtPwdConf   = new javax.swing.JPasswordField();
        lblRole      = new javax.swing.JLabel();
        cmbRole      = new javax.swing.JComboBox<>();
        panelBtns    = new javax.swing.JPanel();
        btnSave      = new RoundedButton("💾 Thêm nhân viên", PRIMARY, Color.WHITE, 10);
        btnCancel    = new javax.swing.JButton();
        lblError     = new javax.swing.JLabel();

        setBackground(new java.awt.Color(245, 246, 250));

        btnBack.setText("←");
        btnBack.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20));
        btnBack.setForeground(TEXT_GRAY);
        btnBack.setBackground(new java.awt.Color(245, 246, 250));
        btnBack.setBorderPainted(false); btnBack.setFocusPainted(false); btnBack.setContentAreaFilled(false);
        btnBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBack.addActionListener(e -> doBack());

        lblTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20));
        lblTitle.setForeground(TEXT_DARK); lblTitle.setText("Thêm nhân viên mới");

        lblSubtitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        lblSubtitle.setForeground(TEXT_GRAY); lblSubtitle.setText("Nhập đầy đủ thông tin nhân viên");

        // Form card
        panelForm.setBackground(java.awt.Color.WHITE);
        panelForm.setBorder(javax.swing.BorderFactory.createLineBorder(BORDER, 1, true));

        lblFullName.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblFullName.setText("Họ và tên *"); lblFullName.setForeground(TEXT_DARK);
        txtFullName.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        txtFullName.putClientProperty("JTextField.placeholderText", "Nguyễn Văn A");

        lblUsername.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblUsername.setText("Tên đăng nhập *"); lblUsername.setForeground(TEXT_DARK);
        txtUsername.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        txtUsername.putClientProperty("JTextField.placeholderText", "nva.nv");

        lblPhone.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblPhone.setText("Số điện thoại"); lblPhone.setForeground(TEXT_DARK);
        txtPhone.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        txtPhone.putClientProperty("JTextField.placeholderText", "0912345678");

        lblEmail.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblEmail.setText("Email"); lblEmail.setForeground(TEXT_DARK);
        txtEmail.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        txtEmail.putClientProperty("JTextField.placeholderText", "example@email.com");

        lblPwd.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblPwd.setText("Mật khẩu *"); lblPwd.setForeground(TEXT_DARK);
        txtPwd.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        txtPwd.putClientProperty("JPasswordField.placeholderText", "Tối thiểu 6 ký tự");

        lblPwdConf.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblPwdConf.setText("Xác nhận mật khẩu *"); lblPwdConf.setForeground(TEXT_DARK);
        txtPwdConf.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        txtPwdConf.putClientProperty("JPasswordField.placeholderText", "Nhập lại mật khẩu");

        lblRole.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblRole.setText("Vai trò *"); lblRole.setForeground(TEXT_DARK);
        cmbRole.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        cmbRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Nhân viên","Quản trị viên"}));

        lblError.setText(" "); lblError.setForeground(new java.awt.Color(220,38,38));
        lblError.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));

        // Form GroupLayout
        javax.swing.GroupLayout formGL = new javax.swing.GroupLayout(panelForm);
        panelForm.setLayout(formGL);
        formGL.setHorizontalGroup(formGL.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formGL.createSequentialGroup()
                .addGap(24)
                .addGroup(formGL.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFullName)
                    .addComponent(txtFullName, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addComponent(lblUsername)
                    .addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addGroup(formGL.createSequentialGroup()
                        .addGroup(formGL.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPhone)
                            .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20)
                        .addGroup(formGL.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEmail)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblPwd)
                    .addComponent(txtPwd, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addComponent(lblPwdConf)
                    .addComponent(txtPwdConf, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addComponent(lblRole)
                    .addComponent(cmbRole, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblError))
                .addGap(24)));
        formGL.setVerticalGroup(formGL.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formGL.createSequentialGroup()
                .addGap(20)
                .addComponent(lblFullName).addGap(4).addComponent(txtFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14)
                .addComponent(lblUsername).addGap(4).addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14)
                .addGroup(formGL.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblPhone).addComponent(lblEmail))
                .addGap(4)
                .addGroup(formGL.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14)
                .addComponent(lblPwd).addGap(4).addComponent(txtPwd, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14)
                .addComponent(lblPwdConf).addGap(4).addComponent(txtPwdConf, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14)
                .addComponent(lblRole).addGap(4).addComponent(cmbRole, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10).addComponent(lblError)
                .addGap(20)));

        // Buttons
        panelBtns.setOpaque(false);
        panelBtns.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));
        btnSave.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btnSave.setPreferredSize(new java.awt.Dimension(185, 44));
        btnSave.addActionListener(e -> doSave());

        btnCancel.setText("Hủy");
        btnCancel.setBackground(new java.awt.Color(241,245,249));
        btnCancel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        btnCancel.setPreferredSize(new java.awt.Dimension(80, 44));
        btnCancel.setBorderPainted(false); btnCancel.setFocusPainted(false);
        btnCancel.addActionListener(e -> doBack());
        panelBtns.add(btnSave); panelBtns.add(Box.createHorizontalStrut(12)); panelBtns.add(btnCancel);

        // Outer panel
        panelOuter = new javax.swing.JPanel();
        panelOuter.setBackground(new java.awt.Color(245, 246, 250));
        javax.swing.GroupLayout outerGL = new javax.swing.GroupLayout(panelOuter);
        panelOuter.setLayout(outerGL);
        outerGL.setHorizontalGroup(outerGL.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outerGL.createSequentialGroup()
                .addGap(24)
                .addGroup(outerGL.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(outerGL.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6)
                        .addGroup(outerGL.createParallelGroup().addComponent(lblTitle).addComponent(lblSubtitle)))
                    .addComponent(panelForm, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                    .addComponent(panelBtns))
                .addGap(24)));
        outerGL.setVerticalGroup(outerGL.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outerGL.createSequentialGroup()
                .addGap(20)
                .addGroup(outerGL.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnBack).addComponent(lblTitle))
                .addGap(2).addComponent(lblSubtitle)
                .addGap(16).addComponent(panelForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16).addComponent(panelBtns)
                .addGap(24)));

        scrollOuter.setViewportView(panelOuter);
        scrollOuter.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        scrollOuter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollOuter.getViewport().setBackground(new java.awt.Color(245, 246, 250));

        setLayout(new BorderLayout());
        add(scrollOuter, BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    public void loadForEdit(User u) {
        if (u == null) return;
        editId = u.getId();
        txtFullName.setText(u.getFullName() == null ? "" : u.getFullName());
        txtUsername.setText(u.getUsername() == null ? "" : u.getUsername());
        txtPhone.setText(u.getPhone() == null ? "" : u.getPhone());
        txtEmail.setText(u.getEmail() == null ? "" : u.getEmail());
        cmbRole.setSelectedItem("ADMIN".equalsIgnoreCase(u.getRole()) ? "Quản trị viên" : "Nhân viên");
        txtPwd.setText("");
        txtPwdConf.setText("");
        lblError.setText(" ");
        lblTitle.setText("Chỉnh sửa nhân viên");
        lblSubtitle.setText("Cập nhật thông tin nhân viên");
        lblPwd.setText("Mật khẩu mới");
        lblPwdConf.setText("Xác nhận mật khẩu mới");
        btnSave.setText("💾 Cập nhật nhân viên");
    }

    public void resetNew() {
        editId = null;
        txtFullName.setText("");
        txtUsername.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtPwd.setText("");
        txtPwdConf.setText("");
        cmbRole.setSelectedIndex(0);
        lblError.setText(" ");
        lblTitle.setText("Thêm nhân viên mới");
        lblSubtitle.setText("Nhập đầy đủ thông tin nhân viên");
        lblPwd.setText("Mật khẩu *");
        lblPwdConf.setText("Xác nhận mật khẩu *");
        btnSave.setText("💾 Thêm nhân viên");
    }

    private void doSave() {
        lblError.setText(" ");
        String fn  = txtFullName.getText().trim();
        String un  = txtUsername.getText().trim();
        String ph  = txtPhone.getText().trim();
        String em  = txtEmail.getText().trim();
        String pwd = new String(txtPwd.getPassword());
        String cPwd= new String(txtPwdConf.getPassword());
        boolean editing = editId != null;

        if (fn.isEmpty())  { setError("Vui lòng nhập họ và tên."); txtFullName.requestFocus(); return; }
        if (un.isEmpty())  { setError("Vui lòng nhập tên đăng nhập."); txtUsername.requestFocus(); return; }
        if (!editing && pwd.length()<6){ setError("Mật khẩu tối thiểu 6 ký tự."); txtPwd.requestFocus(); return; }
        if (editing && (!pwd.isEmpty() || !cPwd.isEmpty()) && pwd.length() < 6) {
            setError("Mật khẩu mới tối thiểu 6 ký tự."); txtPwd.requestFocus(); return;
        }
        if ((editing && (!pwd.isEmpty() || !cPwd.isEmpty()) || !editing) && !pwd.equals(cPwd)) {
            setError("Mật khẩu xác nhận không khớp."); txtPwdConf.requestFocus(); return;
        }

        String role = "Quản trị viên".equals(cmbRole.getSelectedItem()) ? "ADMIN" : "STAFF";
        Long eid = editId;
        btnSave.setEnabled(false);
        new SwingWorker<String, Void>() {
            @Override protected String doInBackground() throws Exception {
                if (eid == null) {
                    if (userDAO.usernameExists(un)) return "USERNAME_EXISTS";
                    userDAO.addUser(fn, un, ph.isEmpty()?null:ph, em.isEmpty()?null:em, pwd, role);
                } else {
                    if (userDAO.usernameExistsExceptId(un, eid)) return "USERNAME_EXISTS";
                    userDAO.updateUser(eid, fn, un, ph.isEmpty()?null:ph, em.isEmpty()?null:em,
                            pwd.trim().isEmpty() ? null : pwd, role);
                }
                return "OK";
            }
            @Override protected void done() {
                btnSave.setEnabled(true);
                try {
                    String r = get();
                    if ("USERNAME_EXISTS".equals(r)) { setError("Tên đăng nhập đã tồn tại!"); }
                    else {
                        JOptionPane.showMessageDialog(
                                PanelThemNV.this,
                                eid == null ? "✅ Thêm nhân viên thành công!" : "✅ Cập nhật nhân viên thành công!"
                        );
                        doBack();
                    }
                } catch(Exception ex) { setError("Lỗi: "+ex.getMessage()); }
            }
        }.execute();
    }

    private void setError(String msg) { lblError.setText(msg); }

    private void doBack() {
        resetNew();
        if (dashboard != null) {
            PanelQuanLyNV p = dashboard.getPanelQuanLyNV();
            if (p != null) p.loadData();
            dashboard.showPanel(Dashboard.PANEL_QUAN_LY_NV);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton         btnBack;
    private javax.swing.JButton         btnCancel;
    private javax.swing.JButton         btnSave;
    private javax.swing.JComboBox<String> cmbRole;
    private javax.swing.JLabel          lblEmail;
    private javax.swing.JLabel          lblError;
    private javax.swing.JLabel          lblFullName;
    private javax.swing.JLabel          lblPhone;
    private javax.swing.JLabel          lblPwd;
    private javax.swing.JLabel          lblPwdConf;
    private javax.swing.JLabel          lblRole;
    private javax.swing.JLabel          lblSubtitle;
    private javax.swing.JLabel          lblTitle;
    private javax.swing.JLabel          lblUsername;
    private javax.swing.JPanel          panelBtns;
    private javax.swing.JPanel          panelForm;
    private javax.swing.JPanel          panelOuter;
    private javax.swing.JTextField     txtEmail;
    private javax.swing.JPasswordField  txtPwd;
    private javax.swing.JPasswordField  txtPwdConf;
    private javax.swing.JScrollPane     scrollOuter;
    private javax.swing.JTextField      txtFullName;
    private javax.swing.JTextField      txtPhone;
    private javax.swing.JTextField      txtUsername;
    // End of variables declaration//GEN-END:variables
}
