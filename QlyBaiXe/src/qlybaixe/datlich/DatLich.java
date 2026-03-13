package qlybaixe.datlich;

import java.awt.*;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import qlybaixe.dao.BookingDAO;
import qlybaixe.model.User;

/**
 * Màn hình Quản lý đặt sân – chuẩn NetBeans JFrame Form
 */
public class DatLich extends javax.swing.JFrame {

    // ── Màu ─────────────────────────────────────────────────────
    private static final Color PRIMARY      = new Color(13,  71, 161);
    private static final Color SIDEBAR_BG   = new Color(249, 250, 251);
    private static final Color SIDEBAR_SEL  = new Color(235, 240, 255);
    private static final Color TEXT_DARK    = new Color(30,  41,  59);
    private static final Color TEXT_GRAY    = new Color(107, 114, 128);
    private static final Color BORDER_CLR   = new Color(229, 231, 235);

    private static final DecimalFormat FMT  = new DecimalFormat("#,###");
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("d/M/yyyy");
    private static final DateTimeFormatter HM_FMT   = DateTimeFormatter.ofPattern("HH:mm");

    // ── DAO / State ──────────────────────────────────────────────
    private final BookingDAO bookingDAO = new BookingDAO();
    private final User currentUser;
    private JButton selectedNav;

    // ── IDs của từng dòng (để action) ───────────────────────────
    private final java.util.List<Long>   rowIds      = new java.util.ArrayList<>();
    private final java.util.List<String> rowStatuses = new java.util.ArrayList<>();

    public DatLich(User user) {
        this.currentUser = user;
        initComponents();
        setupAfterInit();
    }

    public DatLich() {
        this(null);
    }

    // ════════════════════════════════════════════════════════════
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelHeader  = new javax.swing.JPanel();
        lblAppTitle  = new javax.swing.JLabel();
        lblUserName  = new javax.swing.JLabel();
        lblUserRole  = new javax.swing.JLabel();
        btnLogout    = new javax.swing.JButton();
        panelMain    = new javax.swing.JPanel();
        panelSidebar = new javax.swing.JPanel();
        btnTongQuan       = new javax.swing.JButton();
        btnDatLich        = new javax.swing.JButton();
        btnLichSan        = new javax.swing.JButton();
        btnQuanLySan      = new javax.swing.JButton();
        btnQuanLyNV       = new javax.swing.JButton();
        btnBaoCao         = new javax.swing.JButton();
        btnLichSu         = new javax.swing.JButton();
        panelContent = new javax.swing.JPanel();
        lblTitle     = new javax.swing.JLabel();
        lblSubtitle  = new javax.swing.JLabel();
        btnTaoDon    = new javax.swing.JButton();
        scrollTable  = new javax.swing.JScrollPane();
        tblBookings  = new javax.swing.JTable();

        // ── Frame ────────────────────────────────────────────────
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản Lý Sân Thể Thao – Đặt lịch");
        setMinimumSize(new java.awt.Dimension(1100, 650));

        // ── Header ───────────────────────────────────────────────
        panelHeader.setBackground(java.awt.Color.WHITE);
        panelHeader.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_CLR));

        lblAppTitle.setFont(new java.awt.Font("Segoe UI", 1, 16));
        lblAppTitle.setForeground(PRIMARY);
        lblAppTitle.setText("Quản Lý Sân Thể Thao");

        lblUserName.setFont(new java.awt.Font("Segoe UI", 1, 13));
        lblUserName.setForeground(TEXT_DARK);
        lblUserName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUserName.setText("Người dùng");

        lblUserRole.setFont(new java.awt.Font("Segoe UI", 0, 11));
        lblUserRole.setForeground(TEXT_GRAY);
        lblUserRole.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUserRole.setText("Nhân viên");

        btnLogout.setBackground(new java.awt.Color(254, 242, 242));
        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 12));
        btnLogout.setForeground(new java.awt.Color(239, 68, 68));
        btnLogout.setText("→ Đăng xuất");
        btnLogout.setBorderPainted(false);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogout.addActionListener(e -> btnLogoutActionPerformed(e));

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addGap(20).addComponent(lblAppTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUserName).addGap(2).addComponent(lblUserRole)
                .addGap(16).addComponent(btnLogout).addGap(20))
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAppTitle).addComponent(lblUserName).addComponent(btnLogout))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelHeaderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUserRole).addGap(6))
        );

        // ── Sidebar ──────────────────────────────────────────────
        panelSidebar.setBackground(SIDEBAR_BG);
        panelSidebar.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, BORDER_CLR));
        panelSidebar.setPreferredSize(new java.awt.Dimension(170, 600));

        btnTongQuan  = makeSidebarBtn("  \uD83D\uDCCA  Tổng quan");
        btnDatLich   = makeSidebarBtn("  \uD83D\uDCC5  Đặt lịch");
        btnLichSan   = makeSidebarBtn("  \uD83D\uDDD3  Lịch sân");
        btnQuanLySan = makeSidebarBtn("  \u229E  Quản lý sân");
        btnQuanLyNV  = makeSidebarBtn("  \uD83D\uDC64  Quản lý nhân viên");
        btnBaoCao    = makeSidebarBtn("  \uD83D\uDCCA  Báo cáo doanh thu");
        btnLichSu    = makeSidebarBtn("  \uD83D\uDD53  Lịch sử");

        javax.swing.GroupLayout sideLayout = new javax.swing.GroupLayout(panelSidebar);
        panelSidebar.setLayout(sideLayout);
        sideLayout.setHorizontalGroup(
            sideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnTongQuan, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
            .addComponent(btnDatLich,  javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
            .addComponent(btnLichSan,  javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
            .addComponent(btnQuanLySan,javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
            .addComponent(btnQuanLyNV, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
            .addComponent(btnBaoCao,   javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
            .addComponent(btnLichSu,   javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
        );
        sideLayout.setVerticalGroup(
            sideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideLayout.createSequentialGroup()
                .addGap(12)
                .addComponent(btnTongQuan, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(2)
                .addComponent(btnDatLich,  javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(2)
                .addComponent(btnLichSan,  javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(2)
                .addComponent(btnQuanLySan,javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(2)
                .addComponent(btnQuanLyNV, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(2)
                .addComponent(btnBaoCao,   javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(2)
                .addComponent(btnLichSu,   javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        // ── Content ──────────────────────────────────────────────
        panelContent.setBackground(new java.awt.Color(245, 246, 250));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 20));
        lblTitle.setForeground(TEXT_DARK);
        lblTitle.setText("Quản lý đặt sân");

        lblSubtitle.setFont(new java.awt.Font("Segoe UI", 0, 13));
        lblSubtitle.setForeground(TEXT_GRAY);
        lblSubtitle.setText("Danh sách tất cả các đơn đặt sân");

        btnTaoDon.setBackground(PRIMARY);
        btnTaoDon.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnTaoDon.setForeground(java.awt.Color.WHITE);
        btnTaoDon.setText("+ Tạo đơn mới");
        btnTaoDon.setBorderPainted(false);
        btnTaoDon.setFocusPainted(false);
        btnTaoDon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTaoDon.addActionListener(e -> btnTaoDonActionPerformed(e));

        // Table
        tblBookings.setFont(new java.awt.Font("Segoe UI", 0, 13));
        tblBookings.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{"MÃ ĐƠN", "KHÁCH HÀNG", "SÂN", "NGÀY", "GIỜ", "TỔNG TIỀN", "TRẠNG THÁI", "THAO TÁC"}
        ) {
            public boolean isCellEditable(int r, int c) { return c == 7; }
        });
        tblBookings.setRowHeight(52);
        tblBookings.setShowGrid(false);
        tblBookings.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblBookings.setSelectionBackground(new java.awt.Color(235, 240, 255));
        tblBookings.getTableHeader().setFont(new java.awt.Font("Segoe UI", 1, 11));
        tblBookings.getTableHeader().setBackground(new java.awt.Color(249, 250, 251));
        tblBookings.getTableHeader().setForeground(TEXT_GRAY);
        tblBookings.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 42));
        tblBookings.setAutoCreateRowSorter(false);
        // Column widths
        int[] colW = {110, 160, 130, 85, 100, 110, 120, 90};
        for (int i = 0; i < colW.length; i++) {
            tblBookings.getColumnModel().getColumn(i).setPreferredWidth(colW[i]);
            if (i == 7) tblBookings.getColumnModel().getColumn(i).setMinWidth(80);
        }
        scrollTable.setViewportView(tblBookings);
        scrollTable.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        scrollTable.getViewport().setBackground(java.awt.Color.WHITE);

        // Wrapper bảng
        javax.swing.JPanel tableCard = new javax.swing.JPanel(new java.awt.BorderLayout());
        tableCard.setBackground(java.awt.Color.WHITE);
        tableCard.setBorder(javax.swing.BorderFactory.createLineBorder(BORDER_CLR, 1, true));
        tableCard.add(scrollTable, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout contentLayout = new javax.swing.GroupLayout(panelContent);
        panelContent.setLayout(contentLayout);
        contentLayout.setHorizontalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentLayout.createSequentialGroup()
                .addGap(24)
                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contentLayout.createSequentialGroup()
                        .addComponent(lblTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTaoDon, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblSubtitle)
                    .addComponent(tableCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24))
        );
        contentLayout.setVerticalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentLayout.createSequentialGroup()
                .addGap(24)
                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle).addComponent(btnTaoDon))
                .addGap(4).addComponent(lblSubtitle)
                .addGap(16)
                .addComponent(tableCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24))
        );

        // ── panelMain ────────────────────────────────────────────
        panelMain.setBackground(new java.awt.Color(245, 246, 250));
        javax.swing.GroupLayout mainLayout = new javax.swing.GroupLayout(panelMain);
        panelMain.setLayout(mainLayout);
        mainLayout.setHorizontalGroup(
            mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainLayout.createSequentialGroup()
                .addComponent(panelSidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(panelContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainLayout.setVerticalGroup(
            mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelSidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        // ── Root ─────────────────────────────────────────────────
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelMain,   javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(panelMain,   javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // ── Event Handlers ────────────────────────────────────────────
    private void btnLogoutActionPerformed(java.awt.event.ActionEvent e) {//GEN-FIRST:event_btnLogoutActionPerformed
        int ok = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn đăng xuất?",
                "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (ok == JOptionPane.YES_OPTION) {
            new qlybaixe.login.Login().setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnTaoDonActionPerformed(java.awt.event.ActionEvent e) {//GEN-FIRST:event_btnTaoDonActionPerformed
        TaoDon taoDon = new TaoDon(currentUser, this);
        taoDon.setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_btnTaoDonActionPerformed

    // ════════════════════════════════════════════════════════════
    //  Setup ngoài vùng GEN
    // ════════════════════════════════════════════════════════════
    private void setupAfterInit() {
        setLocationRelativeTo(null);

        // User info
        if (currentUser != null) {
            lblUserName.setText(currentUser.getFullName());
            lblUserRole.setText("ADMIN".equals(currentUser.getRole()) ? "Quản trị viên" : "Nhân viên");
        }

        // Sidebar highlight
        selectNav(btnDatLich);
        setupNavActions();

        // Style bảng và load data
        styleTableRenderers();
        loadData();
    }

    /** Tạo nút sidebar */
    private JButton makeSidebarBtn(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(TEXT_DARK);
        btn.setBackground(SIDEBAR_BG);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 16, 0, 16));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                if (btn != selectedNav) btn.setBackground(new Color(241, 245, 249));
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                if (btn != selectedNav) btn.setBackground(SIDEBAR_BG);
            }
        });
        return btn;
    }

    private void selectNav(JButton btn) {
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

    private void setupNavActions() {
        btnTongQuan.addActionListener(e -> {
            new qlybaixe.dashboard.Dashboard(currentUser).setVisible(true);
            dispose();
        });
        btnDatLich.addActionListener(e -> selectNav(btnDatLich));
    }

    // ── Load data từ CSDL ──────────────────────────────────────
    public void loadData() {
        new SwingWorker<List<Object[]>, Void>() {
            @Override protected List<Object[]> doInBackground() throws Exception {
                return bookingDAO.getAllBookingRows();
            }
            @Override protected void done() {
                try {
                    List<Object[]> rows = get();
                    DefaultTableModel model = (DefaultTableModel) tblBookings.getModel();
                    model.setRowCount(0);
                    rowIds.clear();
                    rowStatuses.clear();

                    for (Object[] row : rows) {
                        long id      = (Long)   row[0];
                        String code  = (String) row[1];
                        String name  = (String) row[2];
                        String phone = (String) row[3];
                        String court = (String) row[4];
                        java.time.LocalDate date  = (java.time.LocalDate) row[5];
                        java.time.LocalTime start = (java.time.LocalTime) row[6];
                        java.time.LocalTime end   = (java.time.LocalTime) row[7];
                        java.math.BigDecimal total= (java.math.BigDecimal) row[8];
                        String status= (String) row[9];

                        rowIds.add(id);
                        rowStatuses.add(status);

                        model.addRow(new Object[]{
                            code,
                            "<html><b>" + name + "</b><br><font color='gray' size='2'>" + phone + "</font></html>",
                            court,
                            date.format(DATE_FMT),
                            start.format(HM_FMT) + " - " + end.format(HM_FMT),
                            FMT.format(total) + " đ",
                            formatStatus(status),
                            "action"
                        });
                    }

                    if (model.getRowCount() == 0) {
                        model.addRow(new Object[]{"", "Chưa có đơn đặt sân nào", "", "", "", "", "", ""});
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(DatLich.this,
                            "Lỗi tải dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    // ── Style table ───────────────────────────────────────────────
    private void styleTableRenderers() {
        // Cột widths
        int[] widths = {120, 180, 150, 80, 100, 100, 110, 90};
        for (int i = 0; i < widths.length; i++) {
            tblBookings.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        // Cell renderer chung
        DefaultTableCellRenderer cellRdr = new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                setFont(new Font("Segoe UI", Font.PLAIN, 13));
                setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 12, 0, 12));
                if (!sel) {
                    setBackground(r % 2 == 0 ? Color.WHITE : new Color(250, 251, 252));
                    setForeground(TEXT_DARK);
                }
                return this;
            }
        };

        // Áp dụng cho các cột thường (trừ cột TRẠNG THÁI và THAO TÁC)
        for (int i = 0; i < 6; i++) {
            tblBookings.getColumnModel().getColumn(i).setCellRenderer(cellRdr);
        }

        // Cột TRẠNG THÁI – badge màu
        tblBookings.getColumnModel().getColumn(6).setCellRenderer(new TableCellRenderer() {
            @Override public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean foc, int r, int c) {
                String text = v == null ? "" : v.toString();

                Color bg, fg;
                if ("Đã thanh toán".equals(text))    { bg = new Color(220,252,231); fg = new Color(22,163,74); }
                else if ("Chờ thanh toán".equals(text)) { bg = new Color(254,243,199); fg = new Color(161,98,7); }
                else if ("Đã hủy".equals(text))      { bg = new Color(254,226,226); fg = new Color(220,38,38); }
                else                                  { bg = new Color(219,234,254); fg = new Color(29,78,216); }

                JLabel lbl = new JLabel(text, SwingConstants.CENTER);
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
                lbl.setForeground(fg);
                lbl.setBackground(bg);
                lbl.setOpaque(true);
                lbl.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 10, 4, 10));

                JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
                wrapper.setBackground(r % 2 == 0 ? Color.WHITE : new Color(250, 251, 252));
                wrapper.add(lbl);
                return wrapper;
            }
        });

        // Cột THAO TÁC – nút confirm + cancel
        tblBookings.getColumnModel().getColumn(7).setCellRenderer(new ActionRenderer());
        tblBookings.getColumnModel().getColumn(7).setCellEditor(new ActionEditor(this));
    }

    /** Chuyển status DB sang tên hiển thị */
    static String formatStatus(String raw) {
        if (raw == null) return "";
        if ("CHO_THANH_TOAN".equals(raw)) return "Chờ thanh toán";
        if ("DA_THANH_TOAN" .equals(raw)) return "Đã thanh toán";
        if ("DA_HUY"        .equals(raw)) return "Đã hủy";
        if ("HOAN_THANH"    .equals(raw)) return "Hoàn thành";
        return raw;
    }

    /** Xác nhận thanh toán cho dòng tblBookings row idx */
    void confirmPayment(int row) {
        if (row < 0 || row >= rowIds.size()) return;
        long id     = rowIds.get(row);
        String stat = rowStatuses.get(row);
        if (!"CHO_THANH_TOAN".equals(stat)) {
            JOptionPane.showMessageDialog(this, "Đơn này không ở trạng thái chờ thanh toán.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int ok = JOptionPane.showConfirmDialog(this,
                "Xác nhận đã thu tiền mặt cho đơn này?", "Xác nhận thanh toán",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (ok != JOptionPane.YES_OPTION) return;
        long collectedBy = (currentUser != null && currentUser.getId() != null) ? currentUser.getId() : 1L;
        try {
            bookingDAO.confirmPayment(id, collectedBy);
            JOptionPane.showMessageDialog(this, "Xác nhận thanh toán thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Hủy đơn */
    void cancelBooking(int row) {
        if (row < 0 || row >= rowIds.size()) return;
        long id     = rowIds.get(row);
        String stat = rowStatuses.get(row);
        if ("DA_HUY".equals(stat)) {
            JOptionPane.showMessageDialog(this, "Đơn này đã bị hủy rồi.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if ("DA_THANH_TOAN".equals(stat)) {
            JOptionPane.showMessageDialog(this, "Đơn đã thanh toán, không thể hủy.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int ok = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn hủy đơn này?", "Xác nhận hủy",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (ok != JOptionPane.YES_OPTION) return;
        try {
            bookingDAO.cancelBooking(id);
            JOptionPane.showMessageDialog(this, "Đã hủy đơn thành công.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        java.awt.EventQueue.invokeLater(() -> new DatLich().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBaoCao;
    private javax.swing.JButton btnDatLich;
    private javax.swing.JButton btnLichSan;
    private javax.swing.JButton btnLichSu;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnQuanLyNV;
    private javax.swing.JButton btnQuanLySan;
    private javax.swing.JButton btnTaoDon;
    private javax.swing.JButton btnTongQuan;
    private javax.swing.JLabel  lblAppTitle;
    private javax.swing.JLabel  lblSubtitle;
    private javax.swing.JLabel  lblTitle;
    private javax.swing.JLabel  lblUserName;
    private javax.swing.JLabel  lblUserRole;
    private javax.swing.JPanel  panelContent;
    private javax.swing.JPanel  panelHeader;
    private javax.swing.JPanel  panelMain;
    private javax.swing.JPanel  panelSidebar;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JTable  tblBookings;
    // End of variables declaration//GEN-END:variables
}

// ════════════════════════════════════════════════════════════
//  ActionRenderer – render 2 icon nút trong cột THAO TÁC
// ════════════════════════════════════════════════════════════
class ActionRenderer implements TableCellRenderer {
    private final JPanel  panel  = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 6));
    private final JLabel  btnOk  = new JLabel("✅");
    private final JLabel  btnDel = new JLabel("❌");

    ActionRenderer() {
        panel.setOpaque(true);
        btnOk .setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        btnDel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        btnOk .setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnDel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(btnOk);
        panel.add(btnDel);
    }

    @Override public Component getTableCellRendererComponent(
            JTable t, Object v, boolean sel, boolean foc, int r, int c) {
        panel.setBackground(r % 2 == 0 ? Color.WHITE : new Color(250, 251, 252));
        return panel;
    }
}

// ════════════════════════════════════════════════════════════
//  ActionEditor – bắt click trên 2 nút thao tác
// ════════════════════════════════════════════════════════════
class ActionEditor extends DefaultCellEditor {
    private final DatLich parent;
    private final JPanel  panel  = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 6));
    private final JLabel  btnOk  = new JLabel("✅");
    private final JLabel  btnDel = new JLabel("❌");
    private int currentRow = -1;

    ActionEditor(DatLich parent) {
        super(new JCheckBox());
        this.parent = parent;
        setClickCountToStart(1);
        panel.setBackground(Color.WHITE);
        btnOk .setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        btnDel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        btnOk .setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnDel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnOk.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                stopCellEditing();
                parent.confirmPayment(currentRow);
            }
        });
        btnDel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                stopCellEditing();
                parent.cancelBooking(currentRow);
            }
        });
        panel.add(btnOk);
        panel.add(btnDel);
    }

    @Override public Component getTableCellEditorComponent(
            JTable t, Object v, boolean sel, int r, int c) {
        currentRow = r;
        panel.setBackground(r % 2 == 0 ? Color.WHITE : new Color(250, 251, 252));
        return panel;
    }

    @Override public Object getCellEditorValue() { return null; }
}
