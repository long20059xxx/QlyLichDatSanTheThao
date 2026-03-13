package qlybaixe.quanlynhanvien;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import qlybaixe.dao.UserDAO;
import qlybaixe.dashboard.Dashboard;
import qlybaixe.model.User;
import qlybaixe.util.RoundedButton;

/**
 * JPanel - Quan ly nhan vien.
 * Khung suon do NetBeans quan ly, logic nam ben duoi initComponents().
 */
public class PanelQuanLyNV extends javax.swing.JPanel {

    private static final Color PRIMARY = Dashboard.PRIMARY;
    private static final Color TEXT_DARK = Dashboard.TEXT_DARK;
    private static final Color TEXT_GRAY = Dashboard.TEXT_GRAY;
    private static final Color BORDER = Dashboard.BORDER_CLR;

    private final UserDAO userDAO = new UserDAO();
    private final User currentUser;
    private final Dashboard dashboard;

    private final List<Long> rowIds = new ArrayList<>();
    private final List<Boolean> rowActive = new ArrayList<>();
    private final List<User> rowUsers = new ArrayList<>();

    public PanelQuanLyNV(User user, Dashboard dash) {
        this.currentUser = user;
        this.dashboard = dash;
        initComponents();
        setupCustomUI(user, dash);
    }

    public PanelQuanLyNV() {
        this(null, null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        lblSubtitle = new javax.swing.JLabel();
        btnThemNV = new RoundedButton("+ ThÃªm nhÃ¢n viÃªn", PRIMARY, Color.WHITE, 10);
        scrollTable = new javax.swing.JScrollPane();
        tblUsers = new javax.swing.JTable();

        setBackground(new java.awt.Color(245, 246, 250));

        lblTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20));
        lblTitle.setForeground(TEXT_DARK);
        lblTitle.setText("Quáº£n lÃ½ nhÃ¢n viÃªn");

        lblSubtitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        lblSubtitle.setForeground(TEXT_GRAY);
        lblSubtitle.setText("Danh sÃ¡ch táº¥t cáº£ nhÃ¢n viÃªn");

        btnThemNV.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        btnThemNV.setPreferredSize(new java.awt.Dimension(170, 38));
        btnThemNV.addActionListener(e -> openThemNV());

        tblUsers.setModel(new DefaultTableModel(new Object[][]{},
            new String[]{"Há»Œ TÃŠN","USERNAME","Sá» ÄIá»†N THOáº I","EMAIL","TRáº NG THÃI","THAO TÃC"}
        ) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return c == 5;
            }
        });
        tblUsers.setRowHeight(50);
        tblUsers.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        tblUsers.setShowGrid(false);
        tblUsers.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblUsers.setSelectionBackground(new java.awt.Color(235, 240, 255));
        tblUsers.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
        tblUsers.getTableHeader().setBackground(new java.awt.Color(249, 250, 251));
        tblUsers.getTableHeader().setForeground(TEXT_GRAY);
        tblUsers.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 42));

        scrollTable.setViewportView(tblUsers);
        scrollTable.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        scrollTable.getViewport().setBackground(java.awt.Color.WHITE);

        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(javax.swing.BorderFactory.createLineBorder(BORDER, 1, true));
        tableCard.add(scrollTable);

        javax.swing.GroupLayout gl = new javax.swing.GroupLayout(this);
        setLayout(gl);
        gl.setHorizontalGroup(gl.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gl.createSequentialGroup()
                .addGap(24)
                .addGroup(gl.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gl.createSequentialGroup()
                        .addComponent(lblTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                            javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThemNV, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblSubtitle)
                    .addComponent(tableCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24)));
        gl.setVerticalGroup(gl.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gl.createSequentialGroup()
                .addGap(24)
                .addGroup(gl.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblTitle)
                    .addComponent(btnThemNV, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4).addComponent(lblSubtitle)
                .addGap(14)
                .addComponent(tableCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24)));

        styleTable();
    }// </editor-fold>//GEN-END:initComponents

    public void setupCustomUI(User user, Dashboard dash) {
        if (!hasAdminAccess()) {
            denyAccess();
            return;
        }

        lblTitle.setText("Qu\u1ea3n l\u00fd nh\u00e2n vi\u00ean");
        lblSubtitle.setText("Danh s\u00e1ch t\u1ea5t c\u1ea3 nh\u00e2n vi\u00ean");
        btnThemNV.setText("+ Th\u00eam nh\u00e2n vi\u00ean");
        btnThemNV.setFocusPainted(false);
        btnThemNV.setBorderPainted(false);
        btnThemNV.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        initTableModel();
        styleTable();
        loadData();
    }

    private boolean hasAdminAccess() {
        return currentUser != null && "ADMIN".equalsIgnoreCase(currentUser.getRole());
    }

    private void denyAccess() {
        removeAll();
        setLayout(new BorderLayout());

        JPanel deniedPanel = new JPanel(new BorderLayout());
        deniedPanel.setBackground(new Color(245, 246, 250));
        deniedPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel deniedLabel = new JLabel("T\u00e0i kho\u1ea3n hi\u1ec7n t\u1ea1i kh\u00f4ng c\u00f3 quy\u1ec1n truy c\u1eadp trang n\u00e0y.", SwingConstants.CENTER);
        deniedLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        deniedLabel.setForeground(new Color(220, 38, 38));

        deniedPanel.add(deniedLabel, BorderLayout.CENTER);
        add(deniedPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private void initTableModel() {
        tblUsers.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "H\u1ecd t\u00ean", "Username", "S\u0110T", "Email", "Vai tr\u00f2", "Tr\u1ea1ng th\u00e1i", "Thao t\u00e1c"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7;
            }
        });
    }

    private void openThemNV() {
        if (!hasAdminAccess()) {
            return;
        }

        if (dashboard != null) {
            PanelThemNV panel = dashboard.getPanelThemNV();
            if (panel != null) {
                panel.resetNew();
            }
            dashboard.showPanel(Dashboard.PANEL_THEM_NV);
        }
    }

    public void loadData() {
        if (!hasAdminAccess()) {
            return;
        }

        new SwingWorker<List<Object[]>, Void>() {
            @Override
            protected List<Object[]> doInBackground() throws Exception {
                return userDAO.getAllUserRows();
            }

            @Override
            protected void done() {
                try {
                    List<Object[]> rows = get();
                    DefaultTableModel model = (DefaultTableModel) tblUsers.getModel();
                    model.setRowCount(0);
                    rowIds.clear();
                    rowActive.clear();
                    rowUsers.clear();

                    for (Object[] row : rows) {
                        long id = (Long) row[0];
                        boolean active = (Boolean) row[6];

                        User user = new User();
                        user.setId(id);
                        user.setFullName((String) row[1]);
                        user.setUsername((String) row[2]);
                        user.setPhone((String) row[3]);
                        user.setEmail((String) row[4]);
                        user.setRole((String) row[5]);
                        user.setActive(active);

                        rowIds.add(id);
                        rowActive.add(active);
                        rowUsers.add(user);

                        model.addRow(new Object[]{
                            id,
                            row[1],
                            row[2],
                            row[3],
                            row[4],
                            formatRole((String) row[5]),
                            active ? "ACTIVE" : "LOCKED",
                            "action"
                        });
                    }

                    if (model.getRowCount() == 0) {
                        model.addRow(new Object[]{"", "Ch\u01b0a c\u00f3 nh\u00e2n vi\u00ean", "", "", "", "", "", ""});
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            PanelQuanLyNV.this,
                            "Loi tai nhan vien: " + ex.getMessage(),
                            "Loi",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }.execute();
    }

    private void styleTable() {
        if (tblUsers.getColumnModel().getColumnCount() == 0) {
            return;
        }

        tblUsers.setRowHeight(50);
        tblUsers.setShowGrid(false);
        tblUsers.setIntercellSpacing(new Dimension(0, 0));
        tblUsers.setSelectionBackground(new Color(235, 240, 255));
        tblUsers.setSelectionForeground(TEXT_DARK);
        tblUsers.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        tblUsers.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        tblUsers.getTableHeader().setBackground(new Color(249, 250, 251));
        tblUsers.getTableHeader().setForeground(TEXT_GRAY);
        tblUsers.getTableHeader().setPreferredSize(new Dimension(0, 42));

        int[] widths = {70, 180, 120, 120, 180, 110, 120, 110};
        for (int i = 0; i < Math.min(widths.length, tblUsers.getColumnModel().getColumnCount()); i++) {
            tblUsers.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        DefaultTableCellRenderer baseRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setFont(new Font("Segoe UI", Font.PLAIN, 13));
                setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));

                if (!isSelected) {
                    setBackground(row % 2 == 0 ? Color.WHITE : new Color(250, 251, 252));
                    setForeground(TEXT_DARK);
                }
                return this;
            }
        };

        for (int i = 0; i <= Math.min(5, tblUsers.getColumnModel().getColumnCount() - 1); i++) {
            tblUsers.getColumnModel().getColumn(i).setCellRenderer(baseRenderer);
        }

        if (tblUsers.getColumnModel().getColumnCount() > 6) {
            tblUsers.getColumnModel().getColumn(6).setCellRenderer(new NvStatusRenderer());
            tblUsers.getColumnModel().getColumn(7).setCellRenderer(new NvActionRenderer(this));
            tblUsers.getColumnModel().getColumn(7).setCellEditor(new NvActionEditor(this));
        }
    }

    private String formatRole(String role) {
        if ("ADMIN".equalsIgnoreCase(role)) {
            return "Qu\u1ea3n tr\u1ecb vi\u00ean";
        }
        if ("STAFF".equalsIgnoreCase(role)) {
            return "Nh\u00e2n vi\u00ean";
        }
        return role == null ? "" : role;
    }

    boolean isRowActive(int row) {
        return row >= 0 && row < rowActive.size() && rowActive.get(row);
    }

    void doEdit(int row) {
        if (row < 0 || row >= rowUsers.size() || !hasAdminAccess()) {
            return;
        }

        if (dashboard != null) {
            PanelThemNV panel = dashboard.getPanelThemNV();
            if (panel != null) {
                panel.loadForEdit(rowUsers.get(row));
                dashboard.showPanel(Dashboard.PANEL_THEM_NV);
            }
        }
    }

    void doToggle(int row) {
        if (row < 0 || row >= rowIds.size() || !hasAdminAccess()) {
            return;
        }

        long id = rowIds.get(row);
        boolean nowActive = rowActive.get(row);

        if (currentUser != null && currentUser.getId() != null && currentUser.getId() == id) {
            JOptionPane.showMessageDialog(
                    this,
                    "B\u1ea1n kh\u00f4ng th\u1ec3 t\u1ef1 kh\u00f3a t\u00e0i kho\u1ea3n \u0111ang \u0111\u0103ng nh\u1eadp.",
                    "C\u1ea3nh b\u00e1o",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String msg = nowActive ? "Kh\u00f3a t\u00e0i kho\u1ea3n n\u00e0y?" : "M\u1edf kh\u00f3a t\u00e0i kho\u1ea3n n\u00e0y?";
        if (JOptionPane.showConfirmDialog(this, msg, "X\u00e1c nh\u1eadn", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
            return;
        }

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                userDAO.toggleUserStatus(id);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    loadData();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            PanelQuanLyNV.this,
                            "L\u1ed7i c\u1eadp nh\u1eadt t\u00e0i kho\u1ea3n: " + ex.getMessage(),
                            "L\u1ed7i",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }.execute();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThemNV;
    private javax.swing.JLabel lblSubtitle;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JTable tblUsers;
    // End of variables declaration//GEN-END:variables
}

class NvStatusRenderer implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        boolean active = "ACTIVE".equals(value == null ? "" : value.toString());

        JLabel label = new JLabel(active ? "Ho\u1ea1t \u0111\u1ed9ng" : "B\u1ecb kh\u00f3a", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 11));
        label.setForeground(active ? new Color(22, 163, 74) : new Color(220, 38, 38));
        label.setBackground(active ? new Color(220, 252, 231) : new Color(254, 226, 226));
        label.setOpaque(true);
        label.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        wrapper.setBackground(row % 2 == 0 ? Color.WHITE : new Color(250, 251, 252));
        wrapper.add(label);
        return wrapper;
    }
}

class NvActionRenderer implements TableCellRenderer {

    private final PanelQuanLyNV parent;
    private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));
    private final JLabel edit = new JLabel("✏");
    private final JLabel toggle = new JLabel();

    NvActionRenderer(PanelQuanLyNV parent) {
        this.parent = parent;

        panel.setOpaque(true);

        Font iconFont = new Font("Segoe UI Emoji", Font.PLAIN, 16);
        edit.setFont(iconFont);
        toggle.setFont(iconFont);

        panel.add(edit);
        panel.add(toggle);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        toggle.setText(parent.isRowActive(row) ? "🔒" : "🔓");
        panel.setBackground(row % 2 == 0 ? Color.WHITE : new Color(250, 251, 252));
        return panel;
    }
}

class NvActionEditor extends DefaultCellEditor {

    private final PanelQuanLyNV parent;
    private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));
    private final JLabel edit = new JLabel("✏");
    private final JLabel toggle = new JLabel();
    private int currentRow = -1;

    NvActionEditor(PanelQuanLyNV parent) {
        super(new JCheckBox());
        this.parent = parent;
        setClickCountToStart(1);

        Font iconFont = new Font("Segoe UI Emoji", Font.PLAIN, 16);
        edit.setFont(iconFont);
        toggle.setFont(iconFont);

        Cursor hand = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
        edit.setCursor(hand);
        toggle.setCursor(hand);

        edit.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                stopCellEditing();
                parent.doEdit(currentRow);
            }
        });

        toggle.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                stopCellEditing();
                parent.doToggle(currentRow);
            }
        });

        panel.add(edit);
        panel.add(toggle);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
                                                 int row, int column) {
        currentRow = row;
        toggle.setText(parent.isRowActive(row) ? "🔒" : "🔓");
        panel.setBackground(row % 2 == 0 ? Color.WHITE : new Color(250, 251, 252));
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "action";
    }
}
