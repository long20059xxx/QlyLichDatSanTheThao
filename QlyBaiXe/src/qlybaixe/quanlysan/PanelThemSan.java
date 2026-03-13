package qlybaixe.quanlysan;

import java.awt.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import javax.swing.*;
import qlybaixe.dao.CourtDAO;
import qlybaixe.dashboard.Dashboard;
import qlybaixe.model.Court;
import qlybaixe.model.User;
import qlybaixe.util.RoundedButton;

/**
 * JPanel – Form thêm / chỉnh sửa sân. Chuẩn NetBeans JPanel Form.
 */
public class PanelThemSan extends javax.swing.JPanel {

    private static final Color PRIMARY   = Dashboard.PRIMARY;
    private static final Color TEXT_DARK = Dashboard.TEXT_DARK;
    private static final Color TEXT_GRAY = Dashboard.TEXT_GRAY;
    private static final Color BORDER    = Dashboard.BORDER_CLR;
    private static final DecimalFormat FMT = new DecimalFormat("#,###");

    private final CourtDAO  courtDAO = new CourtDAO();
    private final User      currentUser;
    private final Dashboard dashboard;
    private Long editId = null;   // null = tạo mới, có giá trị = chỉnh sửa

    public PanelThemSan(User user, Dashboard dash) {
        this.currentUser = user;
        this.dashboard   = dash;
        initComponents();
    }
    public PanelThemSan() { this(null, null); }

    private boolean hasAdminAccess() {
        return currentUser != null && "ADMIN".equalsIgnoreCase(currentUser.getRole());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollOuter  = new javax.swing.JScrollPane();
        panelOuter   = new javax.swing.JPanel();
        btnBack      = new javax.swing.JButton();
        lblTitle     = new javax.swing.JLabel();
        lblSubtitle  = new javax.swing.JLabel();
        panelForm    = new javax.swing.JPanel();
        lblNameLbl   = new javax.swing.JLabel();
        txtName      = new javax.swing.JTextField();
        lblTypeLbl   = new javax.swing.JLabel();
        cmbType      = new javax.swing.JComboBox<>();
        lblDescLbl   = new javax.swing.JLabel();
        scrDesc      = new javax.swing.JScrollPane();
        txaDesc      = new javax.swing.JTextArea();
        lblPriceLbl  = new javax.swing.JLabel();
        txtPrice     = new javax.swing.JTextField();
        lblStatusLbl = new javax.swing.JLabel();
        cmbStatus    = new javax.swing.JComboBox<>();
        panelBtns    = new javax.swing.JPanel();
        btnSave      = new RoundedButton("💾 Thêm sân", PRIMARY, Color.WHITE, 10);
        btnCancel    = new javax.swing.JButton();

        setBackground(new java.awt.Color(245, 246, 250));

        // Back
        btnBack.setText("←");
        btnBack.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20));
        btnBack.setForeground(TEXT_GRAY);
        btnBack.setBackground(new java.awt.Color(245, 246, 250));
        btnBack.setBorderPainted(false); btnBack.setFocusPainted(false); btnBack.setContentAreaFilled(false);
        btnBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBack.addActionListener(e -> doBack());

        lblTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20));
        lblTitle.setForeground(TEXT_DARK); lblTitle.setText("Thêm sân mới");

        lblSubtitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        lblSubtitle.setForeground(TEXT_GRAY); lblSubtitle.setText("Nhập đầy đủ thông tin sân thể thao");

        // Form card
        panelForm.setBackground(java.awt.Color.WHITE);
        panelForm.setBorder(javax.swing.BorderFactory.createLineBorder(BORDER, 1, true));

        lblNameLbl.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblNameLbl.setText("Tên sân *"); lblNameLbl.setForeground(TEXT_DARK);
        txtName.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        txtName.putClientProperty("JTextField.placeholderText", "Ví dụ: Sân Bóng Đá A1");

        lblTypeLbl.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblTypeLbl.setText("Loại sân *"); lblTypeLbl.setForeground(TEXT_DARK);
        cmbType.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        cmbType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{
            "Bóng Đá","Cầu Lông","Tennis","Bóng Rổ"
        }));

        lblDescLbl.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblDescLbl.setText("Mô tả"); lblDescLbl.setForeground(TEXT_DARK);
        txaDesc.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        txaDesc.setRows(4); txaDesc.setLineWrap(true); txaDesc.setWrapStyleWord(true);
        txaDesc.putClientProperty("JTextArea.placeholderText", "Mô tả chi tiết về sân...");
        scrDesc.setViewportView(txaDesc);

        lblPriceLbl.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblPriceLbl.setText("Giá thuê (VNĐ/giờ) *"); lblPriceLbl.setForeground(TEXT_DARK);
        txtPrice.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        txtPrice.setText("0");

        lblStatusLbl.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblStatusLbl.setText("Trạng thái *"); lblStatusLbl.setForeground(TEXT_DARK);
        cmbStatus.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Hoạt động","Bảo trì"}));

        javax.swing.GroupLayout formGL = new javax.swing.GroupLayout(panelForm);
        panelForm.setLayout(formGL);
        formGL.setHorizontalGroup(formGL.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formGL.createSequentialGroup()
                .addGap(24)
                .addGroup(formGL.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNameLbl).addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addComponent(lblTypeLbl).addComponent(cmbType, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addComponent(lblDescLbl).addComponent(scrDesc, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addComponent(lblPriceLbl).addComponent(txtPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addComponent(lblStatusLbl).addComponent(cmbStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE))
                .addGap(24)));
        formGL.setVerticalGroup(formGL.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formGL.createSequentialGroup()
                .addGap(20)
                .addComponent(lblNameLbl).addGap(4).addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14)
                .addComponent(lblTypeLbl).addGap(4).addComponent(cmbType, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14)
                .addComponent(lblDescLbl).addGap(4).addComponent(scrDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14)
                .addComponent(lblPriceLbl).addGap(4).addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14)
                .addComponent(lblStatusLbl).addGap(4).addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20)));

        // Buttons
        panelBtns.setOpaque(false);
        panelBtns.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));
        btnSave.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btnSave.setPreferredSize(new java.awt.Dimension(160, 44));
        btnSave.addActionListener(e -> doSave());

        btnCancel.setText("Hủy");
        btnCancel.setBackground(new java.awt.Color(241,245,249));
        btnCancel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        btnCancel.setPreferredSize(new java.awt.Dimension(80, 44));
        btnCancel.setBorderPainted(false); btnCancel.setFocusPainted(false);
        btnCancel.addActionListener(e -> doBack());
        panelBtns.add(btnSave); panelBtns.add(Box.createHorizontalStrut(12)); panelBtns.add(btnCancel);

        // Outer
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

    // ── Load để chỉnh sửa ────────────────────────────────────────
    public void loadForEdit(Court c) {
        editId = c.getId();
        txtName.setText(c.getName());
        String tp = c.getCourtType();
        cmbType.setSelectedItem(
            "BONG_DA".equals(tp)?"Bóng Đá":
            "CAU_LONG".equals(tp)?"Cầu Lông":
            "TENNIS".equals(tp)?"Tennis":"Bóng Rổ");
        txaDesc.setText(c.getDescription() == null ? "" : c.getDescription());
        txtPrice.setText(c.getPricePerHour() == null ? "0" : c.getPricePerHour().toPlainString());
        cmbStatus.setSelectedItem("HOAT_DONG".equals(c.getStatus()) ? "Hoạt động" : "Bảo trì");
        lblTitle.setText("Chỉnh sửa sân");
        ((JButton) btnSave).setText("💾 Cập nhật");
    }

    /** Reset về chế độ tạo mới */
    public void resetNew() {
        editId = null;
        txtName.setText(""); txaDesc.setText(""); txtPrice.setText("0");
        cmbType.setSelectedIndex(0); cmbStatus.setSelectedIndex(0);
        lblTitle.setText("Thêm sân mới");
        ((JButton) btnSave).setText("💾 Thêm sân");
    }

    private void doSave() {
        if (!hasAdminAccess()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Chỉ Quản trị viên mới được thêm hoặc sửa sân.",
                    "Từ chối truy cập",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String name = txtName.getText().trim();
        if (name.isEmpty()) { JOptionPane.showMessageDialog(this,"Vui lòng nhập tên sân.","Lỗi",JOptionPane.WARNING_MESSAGE); txtName.requestFocus(); return; }
        String tp;
        switch ((String) cmbType.getSelectedItem()) {
            case "Bóng Đá":  tp = "BONG_DA";  break;
            case "Cầu Lông": tp = "CAU_LONG"; break;
            case "Tennis":   tp = "TENNIS";   break;
            default:         tp = "BONG_RO"; break;
        }
        BigDecimal price;
        try { price = new BigDecimal(txtPrice.getText().trim().replace(",","")); }
        catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this,"Giá thuê không hợp lệ.","Lỗi",JOptionPane.WARNING_MESSAGE); return; }
        String status = "Hoạt động".equals(cmbStatus.getSelectedItem()) ? "HOAT_DONG" : "BAO_TRI";
        String desc = txaDesc.getText().trim();
        Long eid = editId;

        btnSave.setEnabled(false);
        new SwingWorker<Void,Void>() {
            @Override protected Void doInBackground() throws Exception {
                if (eid == null) courtDAO.addCourt(name, tp, desc, price, status);
                else             courtDAO.updateCourt(eid, name, tp, desc, price, status);
                return null;
            }
            @Override protected void done() {
                btnSave.setEnabled(true);
                try {
                    get();
                    JOptionPane.showMessageDialog(PanelThemSan.this, eid==null?"✅ Thêm sân thành công!":"✅ Cập nhật thành công!");
                    resetNew();
                    if (dashboard != null) {
                        if (dashboard.getPanelQuanLySan() != null) {
                            dashboard.getPanelQuanLySan().loadData();
                        }
                        dashboard.showPanel(Dashboard.PANEL_QUAN_LY_SAN);
                    }
                } catch (Exception ex) { JOptionPane.showMessageDialog(PanelThemSan.this,"Lỗi: "+ex.getMessage()); }
            }
        }.execute();
    }

    private void doBack() {
        resetNew();
        if (dashboard != null) dashboard.showPanel(Dashboard.PANEL_QUAN_LY_SAN);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton     btnBack;
    private javax.swing.JButton     btnCancel;
    private javax.swing.JButton     btnSave;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JComboBox<String> cmbType;
    private javax.swing.JLabel      lblDescLbl;
    private javax.swing.JLabel      lblNameLbl;
    private javax.swing.JLabel      lblPriceLbl;
    private javax.swing.JLabel      lblStatusLbl;
    private javax.swing.JLabel      lblSubtitle;
    private javax.swing.JLabel      lblTitle;
    private javax.swing.JLabel      lblTypeLbl;
    private javax.swing.JPanel      panelBtns;
    private javax.swing.JPanel      panelForm;
    private javax.swing.JPanel      panelOuter;
    private javax.swing.JScrollPane scrDesc;
    private javax.swing.JScrollPane scrollOuter;
    private javax.swing.JTextArea   txaDesc;
    private javax.swing.JTextField  txtName;
    private javax.swing.JTextField  txtPrice;
    // End of variables declaration//GEN-END:variables
}
