/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newlogin;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Chand
 */
@Entity
@Table(name = "table1", catalog = "password", schema = "")
@NamedQueries({
    @NamedQuery(name = "Table1.findAll", query = "SELECT t FROM Table1 t")
    , @NamedQuery(name = "Table1.findByName", query = "SELECT t FROM Table1 t WHERE t.name = :name")
    , @NamedQuery(name = "Table1.findByEmailid", query = "SELECT t FROM Table1 t WHERE t.emailid = :emailid")
    , @NamedQuery(name = "Table1.findByPassword", query = "SELECT t FROM Table1 t WHERE t.password = :password")})
public class Table1 implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Column(name = "name")
    private String name;
    @Id
    @Basic(optional = false)
    @Column(name = "emailid")
    private String emailid;
    @Column(name = "password")
    private String password;

    public Table1() {
    }

    public Table1(String emailid) {
        this.emailid = emailid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        changeSupport.firePropertyChange("name", oldName, name);
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        String oldEmailid = this.emailid;
        this.emailid = emailid;
        changeSupport.firePropertyChange("emailid", oldEmailid, emailid);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        String oldPassword = this.password;
        this.password = password;
        changeSupport.firePropertyChange("password", oldPassword, password);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (emailid != null ? emailid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Table1)) {
            return false;
        }
        Table1 other = (Table1) object;
        if ((this.emailid == null && other.emailid != null) || (this.emailid != null && !this.emailid.equals(other.emailid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "newlogin.Table1[ emailid=" + emailid + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
