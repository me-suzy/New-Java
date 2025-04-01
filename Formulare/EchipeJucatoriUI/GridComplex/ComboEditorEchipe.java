/*
 * ComboEditorCompartiment.java
 *
 * Created on August 28, 2004, 7:04 PM
 */

package Formulare.EchipeJucatoriUI.GridComplex;

import javax.swing.*;
import javax.swing.JComboBox.*;
import javax.swing.DefaultCellEditor;
import Sursa.*;
import java.math.BigDecimal;
/**
 *
 * @author Bebe
 */

public class ComboEditorEchipe extends javax.swing.DefaultCellEditor{
    
    private javax.swing.JComboBox cboEchipe;
    public ComboEditorEchipe(javax.swing.JComboBox cbo) {
        super(cbo);
        Echipe prodNull=new Echipe();
        prodNull.setIdech(null);
        prodNull.setNumeech("necunoscuta");
        cbo.addItem(prodNull);
        this.cboEchipe=cbo;       
        
    }
    public Object getCellEditorValue()
    {
    return((Echipe)this.cboEchipe.getSelectedItem()).getIdech();
    }
    
    public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table,Object value,boolean isSelected,int row,int column)
    
    {String codchitaraCellCurenta=(String)value;
     
     if(codchitaraCellCurenta==null)
     {cboEchipe.setSelectedIndex(cboEchipe.getItemCount()-1);
      return cboEchipe;
     }
     for (int i=0;i<cboEchipe.getItemCount();i++)
     {Echipe obj=(Echipe)cboEchipe.getItemAt(i);
      if(obj.getCodchitara().equals(codchitaraCellCurenta))
      {cboEchipe.setSelectedIndex(i);
       break;
      }
     }
     return cboEchipe;
    }
}

