/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Imrul
 */
public class viewCourses {
    //private int id;
    private String course_code;
    private String department;
    private String credit;

    
    public viewCourses(String pcourse_code, String pdepartment, String pcredit)
    {
        //this.id = pid;
        this.course_code=pcourse_code;
        this.department=pdepartment;
        this.credit=pcredit;
    }
    
    public String getCourse_code()
    {
        return course_code;
    }
    
    public String getDepartment()
    {
        return department;
    }
    
    public String getCredit()
    {
        return credit;
    }
}
