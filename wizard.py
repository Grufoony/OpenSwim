# -*- coding: utf-8 -*-

################################################################################
## Form generated from reading UI file 'wizard.ui'
##
## Created by: Qt User Interface Compiler version 6.11.1
##
## WARNING! All changes made in this file will be lost when recompiling UI file!
################################################################################

from PySide6.QtCore import (QCoreApplication, QDate, QDateTime, QLocale,
    QMetaObject, QObject, QPoint, QRect,
    QSize, QTime, QUrl, Qt)
from PySide6.QtGui import (QBrush, QColor, QConicalGradient, QCursor,
    QFont, QFontDatabase, QGradient, QIcon,
    QImage, QKeySequence, QLinearGradient, QPainter,
    QPalette, QPixmap, QRadialGradient, QTransform)
from PySide6.QtWidgets import (QApplication, QPlainTextEdit, QSizePolicy, QSpinBox,
    QWidget, QWizard, QWizardPage)

class Ui_Wizard(object):
    def setupUi(self, Wizard):
        if not Wizard.objectName():
            Wizard.setObjectName(u"Wizard")
        Wizard.resize(650, 449)
        self.wizardPage1 = QWizardPage()
        self.wizardPage1.setObjectName(u"wizardPage1")
        self.year = QSpinBox(self.wizardPage1)
        self.year.setObjectName(u"year")
        self.year.setGeometry(QRect(10, 80, 91, 26))
        self.year.setMinimum(0)
        self.year.setMaximum(2100)
        self.name = QPlainTextEdit(self.wizardPage1)
        self.name.setObjectName(u"name")
        self.name.setGeometry(QRect(10, 40, 361, 31))
        self.name.setAutoFillBackground(True)
        self.name.setTabChangesFocus(True)
        Wizard.addPage(self.wizardPage1)
        self.wizardPage2 = QWizardPage()
        self.wizardPage2.setObjectName(u"wizardPage2")
        Wizard.addPage(self.wizardPage2)

        self.retranslateUi(Wizard)

        QMetaObject.connectSlotsByName(Wizard)
    # setupUi

    def retranslateUi(self, Wizard):
        Wizard.setWindowTitle(QCoreApplication.translate("Wizard", u"Wizard", None))
#if QT_CONFIG(tooltip)
        self.year.setToolTip(QCoreApplication.translate("Wizard", u"Year", None))
#endif // QT_CONFIG(tooltip)
#if QT_CONFIG(accessibility)
        self.year.setAccessibleName(QCoreApplication.translate("Wizard", u"Year", None))
#endif // QT_CONFIG(accessibility)
    # retranslateUi

