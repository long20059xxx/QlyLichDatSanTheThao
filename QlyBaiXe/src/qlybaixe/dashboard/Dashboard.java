package qlybaixe.dashboard;

import java.awt.*;
import javax.swing.*;
import qlybaixe.model.User;
import qlybaixe.util.RoundedButton;
import qlybaixe.quanlysan.PanelQuanLySan;
import qlybaixe.quanlysan.PanelThemSan;
import qlybaixe.lichsan.PanelLichSan;
import qlybaixe.quanlynhanvien.PanelQuanLyNV;
import qlybaixe.quanlynhanvien.PanelThemNV;
import qlybaixe.lichsu.PanelLichSu;
import qlybaixe.baocao.PanelBaoCao;

/**
 * Dashboard – JFrame duy nhất của ứng dụng.
 * Header + Sidebar cố định. Nội dung thay đổi qua CardLayout.
 */
public class Dashboard extends javax.swing.JFrame {

    // ── Màu ─────────────────────────────────────────────────────
    public static final Color PRIMARY      = new Color(13,  71, 161);
    public static final Color PRIMARY_LIGHT= new Color(235, 240, 255);
    public static final Color SIDEBAR_BG   = new Color(249, 250, 251);
    public static final Color SIDEBAR_SEL  = new Color(235, 240, 255);
    public static final Color HEADER_BG    = Color.WHITE;
    public static final Color TEXT_DARK    = new Color(30,  41,  59);
    public static final Color TEXT_GRAY    = new Color(107, 114, 128);
    public static final Color BORDER_CLR   = new Color(229, 231, 235);

    // ── Panel IDs ────────────────────────────────────────────────
    public static final String PANEL_TONG_QUAN   = "TONG_QUAN";
    public static final String PANEL_DAT_LICH    = "DAT_LICH";
    public static final String PANEL_TAO_DON     = "TAO_DON";
    public static final String PANEL_LICH_SAN    = "LICH_SAN";
    public static final String PANEL_QUAN_LY_SAN = "QUAN_LY_SAN";
    public static final String PANEL_THEM_SAN    = "THEM_SAN";
    public static final String PANEL_QUAN_LY_NV  = "QUAN_LY_NV";
    public static final String PANEL_THEM_NV     = "THEM_NV";
    public static final String PANEL_LICH_SU     = "LICH_SU";
    public static final String PANEL_BAO_CAO     = "BAO_CAO";

    // ── State ────────────────────────────────────────────────────
    private final User currentUser;
    private JButton    selectedNav;

    // ── Sub-panels ───────────────────────────────────────────────
    private PanelTongQuan                 panelTongQuan;
    private qlybaixe.datlich.PanelDatLich panelDatLich;
    private qlybaixe.datlich.PanelTaoDon  panelTaoDon;
    private PanelLichSan    panelLichSan;
    private PanelQuanLySan  panelQuanLySan;
    private PanelThemSan    panelThemSan;
    private PanelQuanLyNV   panelQuanLyNV;
    private PanelThemNV     panelThemNV;
    private PanelLichSu     panelLichSu;
    private PanelBaoCao     panelBaoCao;

    // ── Getters cho cross-panel navigation ───────────────────────
    public PanelThemSan    getPanelThemSan()   { return panelThemSan; }
    public PanelQuanLySan  getPanelQuanLySan() { return panelQuanLySan; }
    public PanelQuanLyNV   getPanelQuanLyNV()  { return panelQuanLyNV; }
    public PanelThemNV     getPanelThemNV()    { return panelThemNV; }
    public User            getCurrentUser()    { return currentUser; }

    public Dashboard(User user) {
        this.currentUser = user;
        initComponents();
        setupAfterInit();
    }

    public Dashboard() { this(null); }

    // ════════════════════════════════════════════════════════════
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelHeader = new javax.swing.JPanel();
        lblAppTitle = new javax.swing.JLabel();
        pnlUserInfo = new javax.swing.JPanel();
        lblUserName = new javax.swing.JLabel();
        lblUserRole = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        panelBody = new javax.swing.JPanel();
        panelSidebar = new javax.swing.JPanel();
        btnTongQuan = new javax.swing.JButton();
        btnDatLich = new javax.swing.JButton();
        btnLichSan = new javax.swing.JButton();
        btnQuanLySan = new javax.swing.JButton();
        btnQuanLyNV = new javax.swing.JButton();
        btnBaoCao = new javax.swing.JButton();
        btnLichSu = new javax.swing.JButton();
        panelContentArea = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản Lý Sân Thể Thao");
        setMinimumSize(new java.awt.Dimension(1140, 700));

        panelHeader.setBackground(new java.awt.Color(255, 255, 255));
        panelHeader.setPreferredSize(new java.awt.Dimension(1140, 60));

        lblAppTitle.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        lblAppTitle.setForeground(new java.awt.Color(13, 71, 161));
        lblAppTitle.setText("Quản Lý Sân Thể Thao");

        pnlUserInfo.setOpaque(false);
        pnlUserInfo.setLayout(new java.awt.BorderLayout());

        lblUserName.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblUserName.setForeground(new java.awt.Color(30, 41, 59));
        lblUserName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUserName.setText("Người dùng");
        pnlUserInfo.add(lblUserName, java.awt.BorderLayout.CENTER);

        lblUserRole.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblUserRole.setForeground(new java.awt.Color(107, 114, 128));
        lblUserRole.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUserRole.setText("Nhân viên");
        pnlUserInfo.add(lblUserRole, java.awt.BorderLayout.SOUTH);

        btnLogout.setBackground(new java.awt.Color(254, 242, 242));
        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(239, 68, 68));
        btnLogout.setText("→ Đăng xuất");
        btnLogout.setBorderPainted(false);
        btnLogout.setFocusPainted(false);

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblAppTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 591, Short.MAX_VALUE)
                .addComponent(pnlUserInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(lblAppTitle)
                .addComponent(pnlUserInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(panelHeader, java.awt.BorderLayout.NORTH);

        panelBody.setBackground(new java.awt.Color(245, 246, 250));
        panelBody.setLayout(new java.awt.BorderLayout());

        panelSidebar.setBackground(new java.awt.Color(249, 250, 251));
        panelSidebar.setPreferredSize(new java.awt.Dimension(180, 600));

        btnTongQuan.setText("  Tổng quan");
        btnTongQuan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTongQuan.setBackground(new java.awt.Color(235, 240, 255));
        btnTongQuan.setForeground(new java.awt.Color(13, 71, 161));
        btnTongQuan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTongQuan.setBorderPainted(false);
        btnTongQuan.setFocusPainted(false);

        btnDatLich.setText("  Đặt lịch");
        btnDatLich.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDatLich.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDatLich.setBorderPainted(false);
        btnDatLich.setFocusPainted(false);

        btnLichSan.setText("  Lịch sân");
        btnLichSan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLichSan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLichSan.setBorderPainted(false);
        btnLichSan.setFocusPainted(false);

        btnQuanLySan.setText("  Quản lý sân");
        btnQuanLySan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnQuanLySan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnQuanLySan.setBorderPainted(false);
        btnQuanLySan.setFocusPainted(false);

        btnQuanLyNV.setText("  Quản lý nhân viên");
        btnQuanLyNV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnQuanLyNV.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnQuanLyNV.setBorderPainted(false);
        btnQuanLyNV.setFocusPainted(false);

        btnBaoCao.setText("  Báo cáo doanh thu");
        btnBaoCao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnBaoCao.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnBaoCao.setBorderPainted(false);
        btnBaoCao.setFocusPainted(false);

        btnLichSu.setText("  Lịch sử");
        btnLichSu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLichSu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLichSu.setBorderPainted(false);
        btnLichSu.setFocusPainted(false);

        javax.swing.GroupLayout panelSidebarLayout = new javax.swing.GroupLayout(panelSidebar);
        panelSidebar.setLayout(panelSidebarLayout);
        panelSidebarLayout.setHorizontalGroup(
            panelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnTongQuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnDatLich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnLichSan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnQuanLySan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnQuanLyNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnBaoCao, javax.swing.GroupLayout.PREFERRED_SIZE, 180, Short.MAX_VALUE)
            .addComponent(btnLichSu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelSidebarLayout.setVerticalGroup(
            panelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSidebarLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(btnTongQuan, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnDatLich, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnLichSan, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnQuanLySan, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnQuanLyNV, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnBaoCao, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnLichSu, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(213, Short.MAX_VALUE))
        );

        panelBody.add(panelSidebar, java.awt.BorderLayout.WEST);

        panelContentArea.setBackground(new java.awt.Color(245, 246, 250));
        panelContentArea.setLayout(new java.awt.CardLayout());
        panelBody.add(panelContentArea, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelBody, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // ════════════════════════════════════════════════════════════
    private void setupAfterInit() {
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // ── Header border ────────────────────────────────────────
        panelHeader.setBorder(javax.swing.BorderFactory.createMatteBorder(0,0,1,0, BORDER_CLR));

        // ── Logout button ────────────────────────────────────────
        btnLogout.addActionListener(e -> doLogout());

        // ── User info ────────────────────────────────────────────
        if (currentUser != null) {
            lblUserName.setText(currentUser.getFullName());
            lblUserRole.setText("ADMIN".equals(currentUser.getRole()) ? "Quản trị viên" : "Nhân viên");
        }

        // ── Apply sidebar styling (NetBeans reset về JButton plain) ──
        javax.swing.JButton[] sidebtns = {
            btnTongQuan, btnDatLich, btnLichSan,
            btnQuanLySan, btnQuanLyNV, btnBaoCao, btnLichSu
        };
        for (javax.swing.JButton b : sidebtns) {
            b.setBackground(SIDEBAR_BG);
            b.setForeground(TEXT_DARK);
            b.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            b.setHorizontalAlignment(SwingConstants.LEFT);
            b.setBorderPainted(false);
            b.setFocusPainted(false);
            b.setContentAreaFilled(true);
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            b.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 18, 0, 12));
            b.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                    if (b != selectedNav) b.setBackground(new Color(241,245,249));
                }
                @Override public void mouseExited(java.awt.event.MouseEvent e) {
                    if (b != selectedNav) b.setBackground(SIDEBAR_BG);
                }
            });
        }

        // ── Sidebar border ───────────────────────────────────────
        panelSidebar.setBorder(javax.swing.BorderFactory.createMatteBorder(0,0,0,1, BORDER_CLR));

        // ── Tạo các sub-panels ────────────────────────────────────────────
        panelTongQuan  = new PanelTongQuan(currentUser, this);
        panelDatLich   = new qlybaixe.datlich.PanelDatLich(currentUser, this);
        panelTaoDon    = new qlybaixe.datlich.PanelTaoDon(currentUser, this);
        panelLichSan   = new PanelLichSan(currentUser, this);
        panelQuanLySan = new PanelQuanLySan(currentUser, this);
        panelThemSan   = new PanelThemSan(currentUser, this);
        panelQuanLyNV  = new PanelQuanLyNV(currentUser, this);
        panelThemNV    = new PanelThemNV(currentUser, this);
        panelLichSu    = new PanelLichSu(currentUser, this);
        panelBaoCao    = new PanelBaoCao(currentUser, this);

        panelContentArea.add(panelTongQuan,  PANEL_TONG_QUAN);
        panelContentArea.add(panelDatLich,   PANEL_DAT_LICH);
        panelContentArea.add(panelTaoDon,    PANEL_TAO_DON);
        panelContentArea.add(panelLichSan,   PANEL_LICH_SAN);
        panelContentArea.add(panelQuanLySan, PANEL_QUAN_LY_SAN);
        panelContentArea.add(panelThemSan,   PANEL_THEM_SAN);
        panelContentArea.add(panelQuanLyNV,  PANEL_QUAN_LY_NV);
        panelContentArea.add(panelThemNV,    PANEL_THEM_NV);
        panelContentArea.add(panelLichSu,    PANEL_LICH_SU);
        panelContentArea.add(panelBaoCao,    PANEL_BAO_CAO);
        applyRolePermissions();

        // ── Nav actions ──────────────────────────────────────────────
        btnTongQuan .addActionListener(e -> showPanel(PANEL_TONG_QUAN,   btnTongQuan));
        btnDatLich  .addActionListener(e -> showPanel(PANEL_DAT_LICH,    btnDatLich));
        btnLichSan  .addActionListener(e -> showPanel(PANEL_LICH_SAN,    btnLichSan));
        btnQuanLySan.addActionListener(e -> showPanel(PANEL_QUAN_LY_SAN, btnQuanLySan));
        btnQuanLyNV .addActionListener(e -> showPanel(PANEL_QUAN_LY_NV,  btnQuanLyNV));
        btnBaoCao   .addActionListener(e -> showPanel(PANEL_BAO_CAO,     btnBaoCao));
        btnLichSu   .addActionListener(e -> showPanel(PANEL_LICH_SU,     btnLichSu));

        // ── Default panel ──────────────────────────────────────────
        showPanel(PANEL_TONG_QUAN, btnTongQuan);
    }


    // ── Public API cho sub-panels gọi ────────────────────────────────────
    public void showPanel(String name) {
        if (!canAccessPanel(name)) {
            showAccessDenied();
            showPanel(PANEL_TONG_QUAN, btnTongQuan);
            return;
        }
        if      (PANEL_TONG_QUAN.equals(name))   showPanel(name, btnTongQuan);
        else if (PANEL_DAT_LICH .equals(name))   showPanel(name, btnDatLich);
        else if (PANEL_LICH_SAN .equals(name))   showPanel(name, btnLichSan);
        else if (PANEL_QUAN_LY_SAN.equals(name)) showPanel(name, btnQuanLySan);
        else if (PANEL_QUAN_LY_NV .equals(name)) showPanel(name, btnQuanLyNV);
        else if (PANEL_LICH_SU  .equals(name))   showPanel(name, btnLichSu);
        else if (PANEL_BAO_CAO  .equals(name))   showPanel(name, btnBaoCao);
        else if (PANEL_TAO_DON.equals(name)) {
            panelTaoDon.reset();
            ((CardLayout) panelContentArea.getLayout()).show(panelContentArea, name);
        } else {
            ((CardLayout) panelContentArea.getLayout()).show(panelContentArea, name);
        }
    }

    private void showPanel(String name, JButton nav) {
        if (!canAccessPanel(name)) {
            showAccessDenied();
            return;
        }
        if (PANEL_TONG_QUAN.equals(name))   panelTongQuan.loadData();
        if (PANEL_DAT_LICH .equals(name))   panelDatLich.loadData();
        if (PANEL_LICH_SAN .equals(name))   panelLichSan.loadData();
        if (PANEL_QUAN_LY_SAN.equals(name)) panelQuanLySan.loadData();
        if (PANEL_QUAN_LY_NV .equals(name)) panelQuanLyNV.loadData();
        if (PANEL_LICH_SU  .equals(name))   panelLichSu.loadData();
        if (PANEL_BAO_CAO  .equals(name))   panelBaoCao.loadData();
        ((CardLayout) panelContentArea.getLayout()).show(panelContentArea, name);
        highlightNav(nav);
    }

    private void highlightNav(JButton btn) {
        if (selectedNav != null) {
            selectedNav.setBackground(SIDEBAR_BG);
            selectedNav.setForeground(TEXT_DARK);
            selectedNav.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
        selectedNav = btn;
        btn.setBackground(SIDEBAR_SEL);
        btn.setForeground(PRIMARY);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    // ── Tạo nút sidebar ──────────────────────────────────────────
    private JButton makeSidebarBtn(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(TEXT_DARK);
        btn.setBackground(SIDEBAR_BG);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 18, 0, 12));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                if (btn != selectedNav) btn.setBackground(new Color(241,245,249));
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                if (btn != selectedNav) btn.setBackground(SIDEBAR_BG);
            }
        });
        return btn;
    }

    private void doLogout() {
        int ok = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn đăng xuất?", "Xác nhận",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (ok == JOptionPane.YES_OPTION) {
            new qlybaixe.login.Login().setVisible(true);
            dispose();
        }
    }

    private boolean isAdminUser() {
        return currentUser != null && "ADMIN".equalsIgnoreCase(currentUser.getRole());
    }

    private void applyRolePermissions() {
        if (isAdminUser()) {
            return;
        }

        JButton[] restrictedButtons = {btnQuanLySan, btnQuanLyNV, btnLichSu};
        for (JButton button : restrictedButtons) {
            button.setEnabled(false);
            button.setVisible(false);
        }
    }

    private boolean canAccessPanel(String name) {
        if (isAdminUser()) {
            return true;
        }

        return PANEL_TONG_QUAN.equals(name)
                || PANEL_DAT_LICH.equals(name)
                || PANEL_TAO_DON.equals(name)
                || PANEL_LICH_SAN.equals(name)
                || PANEL_BAO_CAO.equals(name);
    }

    private void showAccessDenied() {
        JOptionPane.showMessageDialog(
                this,
                "Tài khoản Nhân viên không có quyền truy cập chức năng này.",
                "Từ chối truy cập",
                JOptionPane.WARNING_MESSAGE
        );
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        java.awt.EventQueue.invokeLater(() -> new Dashboard().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBaoCao;
    private javax.swing.JButton btnDatLich;
    private javax.swing.JButton btnLichSan;
    private javax.swing.JButton btnLichSu;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnQuanLyNV;
    private javax.swing.JButton btnQuanLySan;
    private javax.swing.JButton btnTongQuan;
    private javax.swing.JLabel lblAppTitle;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JLabel lblUserRole;
    private javax.swing.JPanel panelBody;
    private javax.swing.JPanel panelContentArea;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelSidebar;
    private javax.swing.JPanel pnlUserInfo;
    // End of variables declaration//GEN-END:variables
}
