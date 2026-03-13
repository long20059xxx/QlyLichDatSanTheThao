package qlybaixe.lichsan;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.*;
import qlybaixe.dao.CourtDAO;
import qlybaixe.dashboard.Dashboard;
import qlybaixe.model.Court;
import qlybaixe.model.User;

/**
 * JPanel – Lịch sân theo ngày (grid view). Chuẩn NetBeans JPanel Form.
 */
public class PanelLichSan extends javax.swing.JPanel {

    private static final Color TEXT_DARK = Dashboard.TEXT_DARK;
    private static final Color TEXT_GRAY = Dashboard.TEXT_GRAY;
    private static final Color BORDER    = Dashboard.BORDER_CLR;

    private static final Color CLR_EMPTY = new Color(248, 250, 252);
    private static final Color CLR_WAIT  = new Color(254, 243, 199);
    private static final Color CLR_PAID  = new Color(220, 252, 231);

    private final CourtDAO  courtDAO = new CourtDAO();
    private List<Court>     allCourts = new ArrayList<>();

    private static final int HOUR_START = 6, HOUR_END = 23;

    public PanelLichSan(User user, Dashboard dash) {
        initComponents();
        initRuntimeComponents();
        setupUI();
        loadCourts();
    }
    public PanelLichSan() { this(null, null); }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);
    }// </editor-fold>//GEN-END:initComponents

    private void initRuntimeComponents() {
        setBackground(new Color(245, 246, 250));
        removeAll();

        panelFilter = new JPanel();
        lblDateLbl = new JLabel();
        dateChooser = new JSpinner();
        lblCourtLbl = new JLabel();
        cmbCourt = new JComboBox();
        btnRefresh = new JButton();
        scrollTable = new JScrollPane();
        tblSchedule = new JTable();
        panelLegend = new JPanel();
        lblLegendTitle = new JLabel();
        lblLegend1 = new JLabel();
        lblLegend2 = new JLabel();
        lblLegend3 = new JLabel();
        lblTitle = new JLabel();
        lblSubtitle = new JLabel();
    }

    /** All UI setup outside GEN section so NetBeans can't overwrite. */
    @SuppressWarnings("unchecked")
    private void setupUI() {
        // ── Labels ───────────────────────────────────────────────
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(TEXT_DARK); lblTitle.setText("Lịch sân theo ngày");

        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitle.setForeground(TEXT_GRAY); lblSubtitle.setText("Xem khung giờ trống/đã đặt của các sân");

        // ── Filter panel ─────────────────────────────────────────
        panelFilter.setBackground(Color.WHITE);
        panelFilter.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1, true),
            BorderFactory.createEmptyBorder(14, 20, 14, 20)));

        lblDateLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblDateLbl.setText("Chọn ngày"); lblDateLbl.setForeground(TEXT_DARK);

        dateChooser.setModel(new SpinnerDateModel());
        JSpinner.DateEditor de = new JSpinner.DateEditor(dateChooser, "dd/MM/yyyy");
        dateChooser.setEditor(de);
        ((JSpinner.DefaultEditor) dateChooser.getEditor()).getTextField()
            .setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dateChooser.setPreferredSize(new Dimension(140, 36));
        dateChooser.addChangeListener(e -> loadSchedule());

        lblCourtLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblCourtLbl.setText("Lọc theo sân"); lblCourtLbl.setForeground(TEXT_DARK);

        cmbCourt.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbCourt.setPreferredSize(new Dimension(200, 36));
        cmbCourt.addActionListener(e -> loadSchedule());

        btnRefresh.setText("🔄 Làm mới");
        btnRefresh.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnRefresh.setBackground(new Color(241, 245, 249)); btnRefresh.setForeground(TEXT_DARK);
        btnRefresh.setBorderPainted(false); btnRefresh.setFocusPainted(false);
        btnRefresh.setPreferredSize(new Dimension(120, 36));
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(e -> loadSchedule());

        JPanel datePnl = new JPanel(new BorderLayout(0, 4)); datePnl.setOpaque(false);
        datePnl.add(lblDateLbl, BorderLayout.NORTH); datePnl.add(dateChooser, BorderLayout.CENTER);
        JPanel courtPnl = new JPanel(new BorderLayout(0, 4)); courtPnl.setOpaque(false);
        courtPnl.add(lblCourtLbl, BorderLayout.NORTH); courtPnl.add(cmbCourt, BorderLayout.CENTER);
        panelFilter.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
        panelFilter.add(datePnl); panelFilter.add(courtPnl); panelFilter.add(btnRefresh);

        // ── Table ─────────────────────────────────────────────────
        buildTableModel();
        scrollTable.setViewportView(tblSchedule);
        scrollTable.setBorder(BorderFactory.createLineBorder(BORDER, 1, true));
        scrollTable.getViewport().setBackground(Color.WHITE);
        scrollTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // ── Legend ────────────────────────────────────────────────
        panelLegend.setBackground(Color.WHITE);
        panelLegend.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1, true),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        panelLegend.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 0));

        lblLegendTitle.setText("Chú thích:");
        lblLegendTitle.setFont(new Font("Segoe UI", Font.BOLD, 12)); lblLegendTitle.setForeground(TEXT_DARK);

        lblLegend1 = makeLegend(CLR_EMPTY, "Còn trống");
        lblLegend2 = makeLegend(CLR_WAIT,  "Chờ thanh toán");
        lblLegend3 = makeLegend(CLR_PAID,  "Đã thanh toán");

        panelLegend.add(lblLegendTitle);
        panelLegend.add(lblLegend1); panelLegend.add(lblLegend2); panelLegend.add(lblLegend3);

        // ── Root layout ───────────────────────────────────────────
        GroupLayout gl = new GroupLayout(this);
        setLayout(gl);
        gl.setHorizontalGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(gl.createSequentialGroup()
                .addGap(24)
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle).addComponent(lblSubtitle)
                    .addComponent(panelFilter,  GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollTable,  GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelLegend,  GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24)));
        gl.setVerticalGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(gl.createSequentialGroup()
                .addGap(24).addComponent(lblTitle)
                .addGap(4).addComponent(lblSubtitle)
                .addGap(14).addComponent(panelFilter,  GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(14).addComponent(scrollTable,  GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10).addComponent(panelLegend,  GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(24)));
    }

    private JLabel makeLegend(Color bg, String txt) {
        JPanel box = new JPanel(); box.setBackground(bg);
        box.setPreferredSize(new Dimension(16, 16));
        box.setBorder(BorderFactory.createLineBorder(BORDER, 1, true));
        JLabel lbl = new JLabel();
        lbl.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 0));
        lbl.add(box);
        JLabel t = new JLabel(txt); t.setFont(new Font("Segoe UI", Font.PLAIN, 12)); t.setForeground(TEXT_DARK);
        lbl.add(t);
        return lbl;
    }

    private void buildTableModel() {
        String[] cols = new String[1 + (HOUR_END - HOUR_START + 1)];
        cols[0] = "SÂN";
        for (int h = HOUR_START; h <= HOUR_END; h++) cols[h - HOUR_START + 1] = String.format("%02d:00", h);

        tblSchedule.setModel(new DefaultTableModel(new Object[][]{}, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        });
        tblSchedule.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblSchedule.setRowHeight(48);
        tblSchedule.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblSchedule.setShowVerticalLines(true); tblSchedule.setGridColor(BORDER);
        tblSchedule.setShowHorizontalLines(true);
        tblSchedule.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        tblSchedule.getTableHeader().setBackground(new Color(249, 250, 251));
        tblSchedule.getTableHeader().setForeground(TEXT_GRAY);
        tblSchedule.getTableHeader().setPreferredSize(new Dimension(0, 38));
        tblSchedule.getColumnModel().getColumn(0).setPreferredWidth(220);
        tblSchedule.getColumnModel().getColumn(0).setMinWidth(180);
        for (int i = 1; i < cols.length; i++) {
            tblSchedule.getColumnModel().getColumn(i).setPreferredWidth(84);
            tblSchedule.getColumnModel().getColumn(i).setMinWidth(84);
        }

        tblSchedule.getColumnModel().getColumn(0).setCellRenderer((table, val, sel, foc, row, col) -> {
            String[] parts = val == null ? new String[]{"",""} : val.toString().split("\n");
            JPanel p = new JPanel(new BorderLayout(0, 2)); p.setBackground(Color.WHITE);
            p.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            JLabel nm = new JLabel(parts[0]); nm.setFont(new Font("Segoe UI", Font.BOLD, 12)); nm.setForeground(TEXT_DARK);
            JLabel tp = new JLabel(parts.length>1?parts[1]:""); tp.setFont(new Font("Segoe UI", Font.PLAIN, 11)); tp.setForeground(TEXT_GRAY);
            p.add(nm, BorderLayout.NORTH); p.add(tp, BorderLayout.CENTER); return p;
        });

        TableCellRenderer slotR = (table, val, sel, foc, row, col) -> {
            String s = val == null ? null : val.toString();
            Color bg; String txt = "Trống";
            if (s == null || "".equals(s)) { bg = CLR_EMPTY; txt = "Trống"; }
            else if ("CHO_THANH_TOAN".equals(s)) { bg = CLR_WAIT; txt = "Chờ TT"; }
            else { bg = CLR_PAID; txt = "Đã TT"; }
            JLabel lbl = new JLabel(txt, SwingConstants.CENTER);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            lbl.setForeground("Trống".equals(txt) ? TEXT_GRAY : TEXT_DARK);
            lbl.setBackground(bg); lbl.setOpaque(true);
            lbl.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
            return lbl;
        };
        for (int i = 1; i < cols.length; i++) tblSchedule.getColumnModel().getColumn(i).setCellRenderer(slotR);
    }

    private void loadCourts() {
        new SwingWorker<List<Court>, Void>() {
            @Override protected List<Court> doInBackground() throws Exception { return courtDAO.getAllActiveCourts(); }
            @Override @SuppressWarnings("unchecked") protected void done() {
                try {
                    allCourts = get();
                    cmbCourt.removeAllItems();
                    cmbCourt.addItem("Tất cả sân");
                    for (Court c : allCourts) cmbCourt.addItem(c.getName());
                    loadSchedule();
                } catch (Exception ex) { /* ignore */ }
            }
        }.execute();
    }

    public void loadData() { loadSchedule(); }

    private void loadSchedule() {
        java.util.Date d = (java.util.Date) dateChooser.getValue();
        LocalDate date = d.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        String filterCourt = (String) cmbCourt.getSelectedItem();

        List<Court> displayCourts;
        if (filterCourt == null || "Tat ca san".equals(filterCourt) || "Tất cả sân".equals(filterCourt)) {
            displayCourts = new java.util.ArrayList<>(allCourts);
        } else {
            displayCourts = new java.util.ArrayList<>();
            for (Court c : allCourts) if (c.getName().equals(filterCourt)) { displayCourts.add(c); break; }
        }

        final List<Court> courts = displayCourts;

        new SwingWorker<List<Object[]>, Void>() {
            @Override protected List<Object[]> doInBackground() throws Exception {
                return courtDAO.getScheduleByDate(date);
            }
            @Override @SuppressWarnings("unchecked") protected void done() {
                try {
                    List<Object[]> raw = get();
                    // Build booking map: courtId -> {hour -> status}
                    Map<Long, Map<Integer,String>> bookingMap = new LinkedHashMap<>();
                    for (Object[] r : raw) {
                        long cid = (Long)r[0];
                        int hr   = (Integer)r[3];
                        String stat = (String)r[4];
                        bookingMap.computeIfAbsent(cid, k -> new java.util.HashMap<>()).put(hr, stat);
                    }

                    DefaultTableModel m = (DefaultTableModel) tblSchedule.getModel();
                    m.setRowCount(0);

                    // Always show all active courts (even those with 0 bookings)
                    for (Court c : courts) {
                        Map<Integer,String> hours = bookingMap.getOrDefault(c.getId(), new java.util.HashMap<>());
                        Object[] row = new Object[1 + (HOUR_END - HOUR_START + 1)];
                        row[0] = c.getName() + "\n" + formatType(c.getCourtType());
                        for (int h = HOUR_START; h <= HOUR_END; h++) {
                            row[h - HOUR_START + 1] = hours.getOrDefault(h, "");
                        }
                        m.addRow(row);
                    }
                    if (m.getRowCount() == 0) m.addRow(buildEmptyRow("Khong co san hoat dong"));
                } catch (Exception ex) { /* ignore */ }
            }
        }.execute();
    }

    private Object[] buildEmptyRow(String msg) {
        int cols = 1 + (HOUR_END - HOUR_START + 1);
        Object[] row = new Object[cols]; row[0] = msg; return row;
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefresh;
    private javax.swing.JComboBox cmbCourt;
    private javax.swing.JSpinner dateChooser;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblCourtLbl;
    private javax.swing.JLabel lblDateLbl;
    private javax.swing.JLabel lblLegend1;
    private javax.swing.JLabel lblLegend2;
    private javax.swing.JLabel lblLegend3;
    private javax.swing.JLabel lblLegendTitle;
    private javax.swing.JLabel lblSubtitle;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel panelFilter;
    private javax.swing.JPanel panelLegend;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JTable tblSchedule;
    // End of variables declaration//GEN-END:variables
}
