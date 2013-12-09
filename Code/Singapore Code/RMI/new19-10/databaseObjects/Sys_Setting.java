package rawobjects;
public class Sys_Setting {

    private String SettingName;
    private String SettingValue;
    private String SettingDesc;
    private String CreatedBy;
    private java.sql.Timestamp CreatedDate;
    private String ModifiedBy;
    private java.sql.Timestamp ModifiedDate;
    public Sys_Setting(){
        SettingName =null;
        SettingValue =null;
        SettingDesc = null;
        CreatedBy = null;
        CreatedDate = null;
        ModifiedBy = null;
        ModifiedDate = null;
    }
    public Sys_Setting(String Name,String Value,String Desc,String CreatedBy,java.sql.Timestamp Date,String Modified,java.sql.Timestamp ModifiedDate){
        this.SettingName = Name;
        this.SettingValue = Value;
        this.SettingDesc = Desc;
        this.CreatedBy = CreatedBy;
        this.CreatedDate = Date;
        this.ModifiedBy = Modified;
        this.ModifiedDate = ModifiedDate;
    }
    public String getName_Setting() {
        return SettingName;
    }

    public void setName_Setting(String pSettingName) {
        SettingName = pSettingName;
    }

    public String getValue_Setting() {
        return SettingValue;
    }

    public void setValue_Setting(String pSetValue) {
        SettingValue = pSetValue;
    }

    public String getDesc_Setting() {
        return SettingDesc;
    }

    public void setDesc_Setting(String pSetDesc) {
        SettingDesc = pSetDesc;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreateBy(String pCreateBy) {
        CreatedBy = pCreateBy;
    }

    public java.sql.Timestamp getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(java.sql.Timestamp pCreateDate) {
        CreatedDate = pCreateDate;
    }

    public String getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(String pModifiedBy) {
        ModifiedBy = pModifiedBy;
    }

    public java.sql.Timestamp getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(java.sql.Timestamp pDateModified) {
        ModifiedDate = pDateModified;
    }
}