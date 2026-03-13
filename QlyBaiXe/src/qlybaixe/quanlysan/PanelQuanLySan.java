package qlybaixe.quanlysan;

import java.awt.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.*;
import qlybaixe.dao.CourtDAO;
import qlybaixe.dashboard.Dashboard;
import qlybaixe.model.Court;
import qlybaixe.model.User;
import qlybaixe.util.RoundedButton;

/**
 * JPanel – Quản lý sân (card grid). Chuẩn NetBeans JPanel Form.
 */
public class PanelQuanLySan extends javax.swing.JPanel {

    private static final Color PRIMARY   = Dashboard.PRIMARY;
    private static final Color TEXT_DARK = Dashboard.TEXT_DARK;
    private static final Color TEXT_GRAY = Dashboard.TEXT_GRAY;
    private static final Color BORDER    = Dashboard.BORDER_CLR;
    private static final DecimalFormat FMT = new DecimalFormat("#,###");

    private final CourtDAO  courtDAO = new CourtDAO();
    private final User      currentUser;
    private final Dashboard dashboard;
    private List<Court> courts;

    public PanelQuanLySan(User user, Dashboard dash) {
        this.currentUser = user;
        this.dashboard   = dash;
        initComponents();
        setupCustomUI();
    }
    public PanelQuanLySan() { this(null, null); }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        lblSubtitle = new javax.swing.JLabel();
        btnThemSan = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        panelGrid = new javax.swing.JPanel();

        setBackground(new java.awt.Color(245, 246, 250));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(33, 37, 41));
        lblTitle.setText("Quản lý sân");

        lblSubtitle.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblSubtitle.setForeground(new java.awt.Color(107, 114, 128));
        lblSubtitle.setText("Danh sách tất cả các sân thể thao");

        btnThemSan.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnThemSan.setText("+ Thêm sân mới");

        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panelGrid.setOpaque(false);
        panelGrid.setLayout(new java.awt.GridLayout(0, 3, 16, 16));
        scrollPane.setViewportView(panelGrid);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 852, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThemSan, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSubtitle)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle)
                    .addComponent(btnThemSan, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(lblSubtitle)
                .addGap(16, 16, 16)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void setupCustomUI() {
        btnThemSan.setFocusPainted(false);
        btnThemSan.setBorderPainted(false);
        btnThemSan.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnThemSan.addActionListener(e -> openThemSan());

        if (!hasAdminAccess()) {
            btnThemSan.setVisible(false);
            btnThemSan.setEnabled(false);
        }
    }

    private boolean hasAdminAccess() {
        return currentUser != null && "ADMIN".equalsIgnoreCase(currentUser.getRole());
    }

    private void openThemSan() {
        if (!hasAdminAccess()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Tài khoản Nhân viên không có quyền thêm hoặc sửa sân.",
                    "Từ chối truy cập",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (dashboard != null) {
            PanelThemSan panel = dashboard.getPanelThemSan();
            if (panel != null) {
                panel.resetNew();
            }
            dashboard.showPanel(Dashboard.PANEL_THEM_SAN);
        }
    }

    // ── Load data ────────────────────────────────────────────────
    public void loadData() {
        new SwingWorker<List<Court>, Void>() {
            @Override protected List<Court> doInBackground() throws Exception { return courtDAO.getAllCourts(); }
            @Override protected void done() {
                try {
                    courts = get();
                    buildGrid();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(PanelQuanLySan.this, "Lỗi tải sân: " + ex.getMessage());
                }
            }
        }.execute();
    }

    private void buildGrid() {
        panelGrid.removeAll();
        for (Court c : courts) panelGrid.add(buildCourtCard(c));
        panelGrid.revalidate();
        panelGrid.repaint();
    }

    private JPanel buildCourtCard(Court c) {
        boolean active = "HOAT_DONG".equals(c.getStatus());
        JPanel card = new JPanel(new BorderLayout(0, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(BORDER, 1, true),
            javax.swing.BorderFactory.createEmptyBorder(14, 16, 12, 16)));

        // Top row : name + badge
        JPanel topRow = new JPanel(new BorderLayout(8, 0));
        topRow.setOpaque(false);
        JLabel lbName = new JLabel(c.getName());
        lbName.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbName.setForeground(TEXT_DARK);
        JLabel badge = new JLabel(active ? "  Hoạt động  " : "  Bảo trì  ");
        badge.setFont(new Font("Segoe UI", Font.BOLD, 10));
        badge.setForeground(active ? new Color(22,163,74) : new Color(161,98,7));
        badge.setBackground(active ? new Color(220,252,231) : new Color(254,243,199));
        badge.setOpaque(true);
        badge.setBorder(javax.swing.BorderFactory.createEmptyBorder(3,8,3,8));
        topRow.add(lbName, BorderLayout.CENTER);
        topRow.add(badge,  BorderLayout.EAST);

        // Middle: type, desc, price
        JPanel mid = new JPanel();
        mid.setOpaque(false);
        mid.setLayout(new BoxLayout(mid, BoxLayout.Y_AXIS));
        mid.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 0, 6, 0));

        JLabel lbType = new JLabel(formatType(c.getCourtType()));
        lbType.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbType.setForeground(new Color(29,78,216));
        lbType.setAlignmentX(LEFT_ALIGNMENT);

        String desc = c.getDescription() == null ? "" : c.getDescription();
        if (desc.length() > 55) desc = desc.substring(0, 52) + "...";
        JLabel lbDesc = new JLabel(desc.isEmpty() ? " " : desc);
        lbDesc.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbDesc.setForeground(TEXT_GRAY);
        lbDesc.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lbAmt = new JLabel(FMT.format(c.getPricePerHour()) + " đ/giờ");
        lbAmt.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lbAmt.setForeground(PRIMARY);
        lbAmt.setAlignmentX(LEFT_ALIGNMENT);

        mid.add(lbType); mid.add(Box.createVerticalStrut(2));
        mid.add(lbDesc); mid.add(Box.createVerticalStrut(6));
        mid.add(lbAmt);

        if (!hasAdminAccess()) {
            card.add(topRow, BorderLayout.NORTH);
            card.add(mid, BorderLayout.CENTER);
            return card;
        }

        // Bottom: separator + buttons
        JSeparator sep = new JSeparator(); sep.setForeground(BORDER);
        JPanel actRow = new JPanel(new BorderLayout(6, 0));
        actRow.setOpaque(false);
        actRow.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 0, 0));

        JButton btnEdit = makeRoundBtn("Sửa", new Color(248,250,252), TEXT_DARK, BORDER);
        btnEdit.addActionListener(e -> editCourt(c));

        String toggleTxt = active ? "Bảo trì" : "Kích hoạt";
        Color  toggleBg  = active ? new Color(254,226,226) : new Color(220,252,231);
        Color  toggleFg  = active ? new Color(220,38,38)   : new Color(22,163,74);
        JButton btnToggle = makeRoundBtn(toggleTxt, toggleBg, toggleFg, toggleBg.darker());
        btnToggle.addActionListener(e -> toggleCourt(c));

        actRow.add(btnEdit, BorderLayout.CENTER); actRow.add(btnToggle, BorderLayout.EAST);

        JPanel bottom = new JPanel(new BorderLayout(0,0)); bottom.setOpaque(false);
        bottom.add(sep, BorderLayout.NORTH); bottom.add(actRow, BorderLayout.CENTER);

        card.add(topRow, BorderLayout.NORTH);
        card.add(mid,    BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);
        return card;
    }

    private JButton makeRoundBtn(String text, Color bg, Color fg, Color border) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(border, 1, true),
            javax.swing.BorderFactory.createEmptyBorder(5, 12, 5, 12)));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private String formatType(String t) {
        if (t == null) return "";
        switch (t) {
            case "BONG_DA":  return "Bóng Đá";
            case "CAU_LONG": return "Cầu Lông";
            case "TENNIS":   return "Tennis";
            case "BONG_RO":  return "Bóng Rổ";
            default: return t;
        }
    }

    private void editCourt(Court c) {
        if (!hasAdminAccess()) {
            return;
        }
        if (dashboard != null) {
            PanelThemSan p = dashboard.getPanelThemSan();
            if (p != null) { p.loadForEdit(c); dashboard.showPanel(Dashboard.PANEL_THEM_SAN); }
        }
    }

    private void toggleCourt(Court c) {
        if (!hasAdminAccess()) {
            return;
        }
        boolean active = "HOAT_DONG".equals(c.getStatus());
        String msg = active ? "Chuyển sân sang Bảo trì?" : "Kích hoạt lại sân này?";
        if (JOptionPane.showConfirmDialog(this, msg, "Xác nhận", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        new SwingWorker<Void, Void>() {
            @Override protected Void doInBackground() throws Exception { courtDAO.toggleCourtStatus(c.getId()); return null; }
            @Override protected void done() {
                try { get(); loadData(); }
                catch (Exception ex) { JOptionPane.showMessageDialog(PanelQuanLySan.this, "Lỗi: " + ex.getMessage()); }
            }
        }.execute();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton     btnThemSan;
    private javax.swing.JLabel      lblSubtitle;
    private javax.swing.JLabel      lblTitle;
    private javax.swing.JPanel      panelGrid;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables
}
