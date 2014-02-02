/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TRTui;

import TRTChart.TRTChart;
import TRTEmail.TRTEmail;
import TRTjson.TRTJson;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.SwingWorker;
import org.jfree.chart.ChartPanel;

/**
 *
 * @author michele
 */
public class TRTGUIui extends javax.swing.JFrame {

    double lastAsk, lastBid;
    Boolean alerting;
    final int REFRESH_RATE = 1500;
    final int SEND_TIME = 250;
    //update last bid and last ask, then verify it for sending alert email
    SwingWorker jsonPriceUpdater = new SwingWorker<Void, Void>() {
        @Override
        public Void doInBackground() throws InterruptedException {
            TRTJson json = new TRTJson();
//initializing
            json.updateAskAndBid();
            lastAsk = json.getCurrAsk();
            lastBid = json.getCurrBid();
            bidValueLabel.setText("" + json.getCurrBid());
            askValueLabel.setText("" + json.getCurrAsk());
            Thread.sleep(500);
            //polling for changing
            while (true) {
                json.updateAskAndBid();

                bidValueLabel.setText("" + json.getCurrBid());
                askValueLabel.setText("" + json.getCurrAsk());

                //set green if it's getting lower and red if it's getting higher
                if (lastAsk > json.getCurrAsk()) {
                    askValueLabel.setBackground(Color.red);
                }

                if (lastAsk < json.getCurrAsk()) {
                    askValueLabel.setBackground(Color.green);
                }

                if (lastBid < json.getCurrBid()) {
                    bidValueLabel.setBackground(Color.red);
                }

                if (lastBid > json.getCurrBid()) {
                    bidValueLabel.setBackground(Color.green);
                }

                lastAsk = json.getCurrAsk();
                lastBid = json.getCurrBid();

                //alerting is set? send (right) email
                if (alerting == true) {
                    
                    //check for more trigger to come
                    if (!bidMinCheckBox.isSelected() && !bidMagCheckBox.isSelected() && !askMagCheckBox.isSelected() && !askMinCheckBox.isSelected())
                        callStop();
                    
                    if (bidMinCheckBox.isSelected() && lastBid < Double.parseDouble(bidMinAlertText.getText())) {
                        bidMinCheckBox.setSelected(false);
                        bidMinSituationLabel.setText("TRIGGERED!");
                        bidMinSituationLabel.setForeground(Color.green);
                        new TRTEmail("BID - LESS THAN " + bidMinAlertText.getText(), emailTextField.getText(), smtpTextField.getText()).send();
                    Thread.sleep(SEND_TIME);
                    }

                    if (bidMagCheckBox.isSelected() && lastBid > Double.parseDouble(bidMagAlertText.getText())) {
                        bidMagCheckBox.setSelected(false);
                        bidMagSituationLabel.setText("TRIGGERED!");
                        bidMagSituationLabel.setForeground(Color.green);
                        new TRTEmail("BID - GREATER THAN " + bidMagAlertText.getText(), emailTextField.getText(), smtpTextField.getText()).send();
                    Thread.sleep(SEND_TIME);
                    }

                    if (askMagCheckBox.isSelected() && lastAsk > Double.parseDouble(askMagAlertText.getText())) {
                        askMagCheckBox.setSelected(false);
                        askMagSituationLabel.setText("TRIGGERED!");
                        askMagSituationLabel.setForeground(Color.green);
                        new TRTEmail("ASK - GREATER THAN " + askMagAlertText.getText(), emailTextField.getText(), smtpTextField.getText()).send();
                    Thread.sleep(SEND_TIME);
                    }

                    if (askMinCheckBox.isSelected() && lastAsk < Double.parseDouble(askMinAlertText.getText())) {
                        askMinCheckBox.setSelected(false);
                        askMinSituationLabel.setText("TRIGGERED!");
                        askMinSituationLabel.setForeground(Color.green);
                        new TRTEmail("ASK - LESS THAN " + askMinAlertText.getText(), emailTextField.getText(), smtpTextField.getText()).send();
                    Thread.sleep(SEND_TIME);
                    }
                }
                Thread.sleep(REFRESH_RATE);
            }
        }
    };

    private void lastTransChartInit() {
        //initializing chart
        TRTChart trtchart = new TRTChart();
        ChartPanel myChart = new ChartPanel(trtchart.updateLastTransChart(1));
        myChart.setMouseWheelEnabled(true);
        chartPanel.setLayout(new java.awt.BorderLayout());
        chartPanel.setPreferredSize(new Dimension(500, 270));
        chartPanel.add(myChart, BorderLayout.CENTER);
        chartPanel.validate();
    }

    /**
     * Creates new form TRTGUIui
     */
    public TRTGUIui() {
        initComponents();

        jsonPriceUpdater.execute();
        lastTransChartInit();
        alerting = false;

        bidMinSituationLabel.setText("");
        askMinSituationLabel.setText("");
        bidMagSituationLabel.setText("");
        askMagSituationLabel.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        errorEmail = new javax.swing.JDialog();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        emailTextField = new javax.swing.JTextField();
        usernameTextField = new javax.swing.JTextField();
        passwordTextField = new javax.swing.JTextField();
        emailLabel = new javax.swing.JLabel();
        userLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        testEmailButton = new javax.swing.JButton();
        smtpComboBox = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        smtpTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        askValueLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        bidValueLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        bidMinAlertLabel = new javax.swing.JLabel();
        bidMinAlertText = new javax.swing.JTextField();
        askMagAlertText = new javax.swing.JTextField();
        startAlertButton = new javax.swing.JButton();
        bidMinCheckBox = new javax.swing.JCheckBox();
        askMagCheckBox = new javax.swing.JCheckBox();
        bidMagAlertText = new javax.swing.JTextField();
        askMinAlertText = new javax.swing.JTextField();
        bidMagAlertLabel = new javax.swing.JLabel();
        bidMagCheckBox = new javax.swing.JCheckBox();
        askMinCheckBox = new javax.swing.JCheckBox();
        askMinAlertLabel = new javax.swing.JLabel();
        askMagAlertLabel = new javax.swing.JLabel();
        stopAlertButton = new javax.swing.JButton();
        bidMinSituationLabel = new javax.swing.JLabel();
        bidMagSituationLabel = new javax.swing.JLabel();
        askMagSituationLabel = new javax.swing.JLabel();
        askMinSituationLabel = new javax.swing.JLabel();
        chartPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        ExitButton = new javax.swing.JButton();

        jLabel3.setText("EMAIL NON CORRETTA");

        jButton1.setText("chiudi");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout errorEmailLayout = new org.jdesktop.layout.GroupLayout(errorEmail.getContentPane());
        errorEmail.getContentPane().setLayout(errorEmailLayout);
        errorEmailLayout.setHorizontalGroup(
            errorEmailLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(errorEmailLayout.createSequentialGroup()
                .add(errorEmailLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(errorEmailLayout.createSequentialGroup()
                        .add(132, 132, 132)
                        .add(jLabel3))
                    .add(errorEmailLayout.createSequentialGroup()
                        .add(152, 152, 152)
                        .add(jButton1)))
                .addContainerGap(125, Short.MAX_VALUE))
        );
        errorEmailLayout.setVerticalGroup(
            errorEmailLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(errorEmailLayout.createSequentialGroup()
                .add(69, 69, 69)
                .add(jLabel3)
                .add(89, 89, 89)
                .add(jButton1)
                .addContainerGap(97, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Information"));

        emailTextField.setText("email address here");
        emailTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                emailTextFieldMouseClicked(evt);
            }
        });
        emailTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailTextFieldActionPerformed(evt);
            }
        });

        usernameTextField.setText("username");
        usernameTextField.setEnabled(false);

        passwordTextField.setText("password");
        passwordTextField.setEnabled(false);

        emailLabel.setText("email");

        userLabel.setText("user");
        userLabel.setEnabled(false);

        passwordLabel.setText("password");
        passwordLabel.setEnabled(false);

        testEmailButton.setText("Send test email");
        testEmailButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testEmailButtonActionPerformed(evt);
            }
        });

        smtpComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "alice", "custom" }));
        smtpComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smtpComboBoxActionPerformed(evt);
            }
        });

        jLabel8.setText("SMTP");

        smtpTextField.setText("out.alice.it");
        smtpTextField.setEnabled(false);

        jLabel4.setText("Provider");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(smtpComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel8)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(smtpTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 254, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(emailLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(emailTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 273, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(testEmailButton))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(userLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(usernameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 108, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(passwordLabel)
                        .add(18, 18, 18)
                        .add(passwordTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(smtpComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel8)
                    .add(smtpTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(emailLabel)
                    .add(emailTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(testEmailButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(userLabel)
                    .add(usernameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(passwordLabel)
                    .add(passwordTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Actual prices"));

        jLabel2.setText("ask");

        askValueLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel1.setText("bid");

        bidValueLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel2)
                .add(18, 18, 18)
                .add(askValueLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 79, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jLabel1)
                .add(18, 18, 18)
                .add(bidValueLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 81, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2Layout.linkSize(new java.awt.Component[] {askValueLabel, bidValueLabel}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabel1)
                    .add(bidValueLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabel2)
                    .add(askValueLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Alert"));

        bidMinAlertLabel.setText("bid <");
        bidMinAlertLabel.setEnabled(false);

        bidMinAlertText.setEnabled(false);
        bidMinAlertText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bidMinAlertTextMouseClicked(evt);
            }
        });

        askMagAlertText.setEnabled(false);
        askMagAlertText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                askMagAlertTextMouseClicked(evt);
            }
        });

        startAlertButton.setText("activate alert");
        startAlertButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startAlertButtonActionPerformed(evt);
            }
        });

        bidMinCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bidMinCheckBoxActionPerformed(evt);
            }
        });

        askMagCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                askMagCheckBoxActionPerformed(evt);
            }
        });

        bidMagAlertText.setEnabled(false);
        bidMagAlertText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bidMagAlertTextMouseClicked(evt);
            }
        });

        askMinAlertText.setEnabled(false);
        askMinAlertText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                askMinAlertTextMouseClicked(evt);
            }
        });
        askMinAlertText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                askMinAlertTextActionPerformed(evt);
            }
        });

        bidMagAlertLabel.setText("bid >");
        bidMagAlertLabel.setEnabled(false);

        bidMagCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bidMagCheckBoxActionPerformed(evt);
            }
        });

        askMinCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                askMinCheckBoxActionPerformed(evt);
            }
        });

        askMinAlertLabel.setText("ask <");
        askMinAlertLabel.setEnabled(false);

        askMagAlertLabel.setText("ask >");
        askMagAlertLabel.setEnabled(false);

        stopAlertButton.setText("STOP");
        stopAlertButton.setEnabled(false);
        stopAlertButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopAlertButtonActionPerformed(evt);
            }
        });

        bidMinSituationLabel.setText("jLabel6");

        bidMagSituationLabel.setText("jLabel7");

        askMagSituationLabel.setText("jLabel9");

        askMinSituationLabel.setText("jLabel10");

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(15, 15, 15)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(bidMinCheckBox)
                    .add(bidMagCheckBox)
                    .add(askMagCheckBox)
                    .add(askMinCheckBox))
                .add(18, 18, 18)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(bidMinAlertLabel)
                    .add(bidMagAlertLabel)
                    .add(askMagAlertLabel)
                    .add(askMinAlertLabel))
                .add(18, 18, 18)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(bidMinAlertText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 112, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(bidMinSituationLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(askMagAlertText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 112, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(askMagSituationLabel))
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(bidMagAlertText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 112, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(bidMagSituationLabel))
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(askMinAlertText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 112, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(askMinSituationLabel)))
                .addContainerGap())
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(startAlertButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(stopAlertButton)
                .add(0, 92, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(new java.awt.Component[] {askMagAlertText, askMinAlertText, bidMagAlertText, bidMinAlertText}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(bidMinCheckBox)
                    .add(bidMinAlertText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(bidMinAlertLabel)
                    .add(bidMinSituationLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(bidMagCheckBox)
                    .add(bidMagAlertLabel)
                    .add(bidMagAlertText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(bidMagSituationLabel))
                .add(5, 5, 5)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(askMagCheckBox)
                    .add(askMagAlertLabel)
                    .add(askMagAlertText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(askMagSituationLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(askMinCheckBox)
                    .add(askMinAlertLabel)
                    .add(askMinAlertText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(askMinSituationLabel))
                .add(18, 19, Short.MAX_VALUE)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(startAlertButton)
                    .add(stopAlertButton)))
        );

        org.jdesktop.layout.GroupLayout chartPanelLayout = new org.jdesktop.layout.GroupLayout(chartPanel);
        chartPanel.setLayout(chartPanelLayout);
        chartPanelLayout.setHorizontalGroup(
            chartPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 253, Short.MAX_VALUE)
        );
        chartPanelLayout.setVerticalGroup(
            chartPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 269, Short.MAX_VALUE)
        );

        jLabel5.setText("if you like this software please consider donating. BTC: 19u1hqFUSoryHdwWb43iQFHgxrE2YyVB8T");

        ExitButton.setText("EXIT");
        ExitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(layout.createSequentialGroup()
                                .add(100, 100, 100)
                                .add(ExitButton)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(chartPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(65, 65, 65))
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jLabel5))
                        .add(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(ExitButton)
                        .add(0, 0, Short.MAX_VALUE))
                    .add(chartPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel5)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startAlertButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startAlertButtonActionPerformed

        if (bidMagAlertText.getText().isEmpty()) {
            bidMagCheckBox.setSelected(false);
            bidMagAlertLabel.setEnabled(false);
        }else
            bidMagSituationLabel.setText("waiting...");
        

        if (bidMinAlertText.getText().isEmpty()) {
            bidMinCheckBox.setSelected(false);
            bidMinAlertLabel.setEnabled(false);
        }else
            bidMinSituationLabel.setText("waiting...");
        

        if (askMagAlertText.getText().isEmpty()) {
            askMagCheckBox.setSelected(false);
            askMagAlertLabel.setEnabled(false);
        }else
            askMagSituationLabel.setText("waiting...");
        

        if (askMinAlertText.getText().isEmpty()) {
            askMinCheckBox.setSelected(false);
            askMinAlertLabel.setEnabled(false);
        }else
            askMinSituationLabel.setText("waiting...");
        

        emailTextField.setEnabled(false);
        testEmailButton.setEnabled(false);

        smtpTextField.setEnabled(false);
        smtpComboBox.setEnabled(false);

        askMinAlertText.setEnabled(false);
        askMagAlertText.setEnabled(false);
        bidMinAlertText.setEnabled(false);
        bidMagAlertText.setEnabled(false);

        askMinCheckBox.setEnabled(false);
        askMagCheckBox.setEnabled(false);
        bidMinCheckBox.setEnabled(false);
        bidMagCheckBox.setEnabled(false);


        stopAlertButton.setEnabled(true);
        startAlertButton.setEnabled(false);
        alerting = true;

    }//GEN-LAST:event_startAlertButtonActionPerformed

    private void bidMinCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bidMinCheckBoxActionPerformed

        bidMinAlertLabel.setEnabled(bidMinCheckBox.isSelected());
        bidMinAlertText.setEnabled(bidMinCheckBox.isSelected());
    }//GEN-LAST:event_bidMinCheckBoxActionPerformed

    private void askMagCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_askMagCheckBoxActionPerformed
        askMagAlertLabel.setEnabled(askMagCheckBox.isSelected());
        askMagAlertText.setEnabled(askMagCheckBox.isSelected());
    }//GEN-LAST:event_askMagCheckBoxActionPerformed

    private void callStop(){
      alerting = false;
        stopAlertButton.setEnabled(false);
        startAlertButton.setEnabled(true);
        emailTextField.setEnabled(true);
        testEmailButton.setEnabled(true);
        smtpTextField.setEnabled(smtpComboBox.getSelectedItem().equals("custom"));
        smtpComboBox.setEnabled(true);

        //reset background color and empty text
        bidMinSituationLabel.setForeground(Color.WHITE);
        askMinSituationLabel.setForeground(Color.WHITE);
        bidMagSituationLabel.setForeground(Color.WHITE);
        askMagSituationLabel.setForeground(Color.WHITE);
        
        bidMinSituationLabel.setText("");
        askMinSituationLabel.setText("");
        bidMagSituationLabel.setText("");
        askMagSituationLabel.setText("");

        //re-enabling threashold?
        bidMagAlertText.setEnabled(bidMagCheckBox.isSelected());
        bidMinAlertText.setEnabled(bidMinCheckBox.isSelected());
        askMinAlertText.setEnabled(askMinCheckBox.isSelected());
        askMagAlertText.setEnabled(askMagCheckBox.isSelected());

        //re-enable trigger system
        askMinCheckBox.setEnabled(true);
        askMagCheckBox.setEnabled(true);
        bidMinCheckBox.setEnabled(true);
        bidMagCheckBox.setEnabled(true);
    }
    private void stopAlertButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopAlertButtonActionPerformed
      callStop();
    }//GEN-LAST:event_stopAlertButtonActionPerformed

    private void bidMagCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bidMagCheckBoxActionPerformed
        bidMagAlertLabel.setEnabled(bidMagCheckBox.isSelected());
        bidMagAlertText.setEnabled(bidMagCheckBox.isSelected());
    }//GEN-LAST:event_bidMagCheckBoxActionPerformed

    private void askMinCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_askMinCheckBoxActionPerformed
        askMinAlertLabel.setEnabled(askMinCheckBox.isSelected());
        askMinAlertText.setEnabled(askMinCheckBox.isSelected());
    }//GEN-LAST:event_askMinCheckBoxActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void bidMinAlertTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bidMinAlertTextMouseClicked
        bidMinAlertText.setBackground(Color.WHITE);
    }//GEN-LAST:event_bidMinAlertTextMouseClicked

    private void bidMagAlertTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bidMagAlertTextMouseClicked
        bidMagAlertText.setBackground(Color.WHITE);
    }//GEN-LAST:event_bidMagAlertTextMouseClicked

    private void askMagAlertTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_askMagAlertTextMouseClicked
        askMagAlertText.setBackground(Color.WHITE);
    }//GEN-LAST:event_askMagAlertTextMouseClicked

    private void askMinAlertTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_askMinAlertTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_askMinAlertTextActionPerformed

    private void askMinAlertTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_askMinAlertTextMouseClicked
        askMinAlertText.setBackground(Color.WHITE);
    }//GEN-LAST:event_askMinAlertTextMouseClicked

    private void smtpComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smtpComboBoxActionPerformed

        smtpTextField.setEnabled(smtpComboBox.getSelectedItem().equals("custom"));
        if(smtpComboBox.getSelectedItem().equals("tiscali"))
                smtpTextField.setText("smtp.tiscali.it");
        if(smtpComboBox.getSelectedItem().equals("alice"))
                smtpTextField.setText("out.alice.it");
    }//GEN-LAST:event_smtpComboBoxActionPerformed

    private void testEmailButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testEmailButtonActionPerformed
        //new test email
        new TRTEmail("TEST EMAIL -- TRTGUI", "The setup is corrected.", emailTextField.getText(), smtpTextField.getText()).send();
    }//GEN-LAST:event_testEmailButtonActionPerformed

    private void emailTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailTextFieldActionPerformed

    private void emailTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_emailTextFieldMouseClicked
        emailTextField.setText(null);
    }//GEN-LAST:event_emailTextFieldMouseClicked

    private void ExitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_ExitButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TRTGUIui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TRTGUIui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TRTGUIui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TRTGUIui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                new TRTGUIui().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ExitButton;
    private javax.swing.JLabel askMagAlertLabel;
    private javax.swing.JTextField askMagAlertText;
    private javax.swing.JCheckBox askMagCheckBox;
    private javax.swing.JLabel askMagSituationLabel;
    private javax.swing.JLabel askMinAlertLabel;
    private javax.swing.JTextField askMinAlertText;
    private javax.swing.JCheckBox askMinCheckBox;
    private javax.swing.JLabel askMinSituationLabel;
    private javax.swing.JLabel askValueLabel;
    private javax.swing.JLabel bidMagAlertLabel;
    private javax.swing.JTextField bidMagAlertText;
    private javax.swing.JCheckBox bidMagCheckBox;
    private javax.swing.JLabel bidMagSituationLabel;
    private javax.swing.JLabel bidMinAlertLabel;
    private javax.swing.JTextField bidMinAlertText;
    private javax.swing.JCheckBox bidMinCheckBox;
    private javax.swing.JLabel bidMinSituationLabel;
    private javax.swing.JLabel bidValueLabel;
    private javax.swing.JPanel chartPanel;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JTextField emailTextField;
    private javax.swing.JDialog errorEmail;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JTextField passwordTextField;
    private javax.swing.JComboBox smtpComboBox;
    private javax.swing.JTextField smtpTextField;
    private javax.swing.JButton startAlertButton;
    private javax.swing.JButton stopAlertButton;
    private javax.swing.JButton testEmailButton;
    private javax.swing.JLabel userLabel;
    private javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables
}
