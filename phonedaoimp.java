package com.example.demo.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.phoneentry;
import com.example.demo.mapper.PhoneRowMapper;

@Repository
public class phonedaoimp implements phonedao{

    public static final Pattern email_regex = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern phone_regex = Pattern.compile("^(?:\\d{10}|\\d{3}.\\d{3}.\\d{4})$", Pattern.CASE_INSENSITIVE);

    //NamedParameterJdbcTemplate is a framework that takes care of all low level details like opening,
    //preparing and executing the sql statement. This is used for sql statements needed below. 
    NamedParameterJdbcTemplate template;  
    
    public phonedaoimp(NamedParameterJdbcTemplate template) {  
        this.template = template;  
    }

    //This returns a list of all entries from phone
    @Override
    public List<phoneentry> findAll() {
    return template.query("select * from phone", new PhoneRowMapper());
    }

    //This inerts an entry in the phone table
    //SqlParameterSource holds a map of the parameters to pass to the update method
    @Override
    public ResponseEntity<String> insertentry(phoneentry pho) {
    final String sql = "insert into phone(phonenumber, firstname,lastname,middleinitial, localaddress, email, id) values(:phonenumber,:firstname,:lastname,:middleinitial,:localaddress,:email,:id)";
        //Check for if the first name is empty
        if (!(isValidName(pho.getfirstname())) || pho.getfirstname().isEmpty() || pho.getfirstname() == null){
            System.out.println("Please enter a valid First Name.");
            return new ResponseEntity<>("Failed to insert entry", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        else if(!(isValidName(pho.getlastname()))){
            System.out.println("Please enter a valid Last Name.");
            return new ResponseEntity<>("Failed to insert entry", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        else if (!(checkMidInt(pho.getmidint()))) {
            System.out.println("Please enter a valid Middle Initial.");
            return new ResponseEntity<>("Failed to insert entry", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        else if(  !(validateemail(pho.getemail())) ){
            System.out.println("Please enter a valid email. EX: 123@example.com");
            System.out.println(HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>("Failed to insert entry", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        else if( !(validatephonum(pho.getphonenumber()))  ){
            System.out.println("Please enter a valid 10 digit phone number with no dashes. Ex: 2692204228");
            System.out.println(pho.getphonenumber());
            return new ResponseEntity<>("Failed to insert entry", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        try{
            SqlParameterSource param = new MapSqlParameterSource()
            .addValue("firstname", pho.getfirstname())
            .addValue("middleinitial", pho.getmidint())
            .addValue("lastname", pho.getlastname())
            .addValue("phonenumber", formatPhoneNumber(pho.getphonenumber()))
            .addValue("localaddress", pho.getaddress())
            .addValue("email", pho.getemail())
            .addValue("id", UUID.randomUUID().toString());
            template.update(sql,param);
            return new ResponseEntity<>("Entry inserted successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("An exception occurred while inserting entry: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>("Failed to insert entry", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    //This updates an entry in the phone table based on the given phone number
    //SqlParameterSource holds a map of the parameters to pass to the update method
    @Override
    public ResponseEntity<String> updateentry(phoneentry pho) {
    final String sql = "update phone set firstname=:firstname, middleinitial=:middleinitial,lastname=:lastname, localaddress=:localaddress, phonenumber=:phonenumber, email=:email where id=:id";
    
        if (pho.getfirstname().isEmpty()  ){
            System.out.println("Please enter a valid First Name.");
            return new ResponseEntity<>("Failed to update entry", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        else if(!(isValidName(pho.getlastname())) ){
            System.out.println("Please enter a valid Last Name.");
            return new ResponseEntity<>("Failed to update entry", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        else if (!(checkMidInt(pho.getmidint()))) {
            System.out.println("Please enter a valid Middle Initial.");
            return new ResponseEntity<>("Failed to update entry", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        else if( !(validateemail(pho.getemail())) && !(pho.getemail().isEmpty())){
            System.out.println("Please enter a valid email. EX: 123@example.com");
            return new ResponseEntity<>("Failed to update entry", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        else if( !(validatephonum(pho.getphonenumber()))  ){
            System.out.println("Please enter a valid 10 digit phone number with no dashes. Ex: 2692204228");
            System.out.println(pho.getphonenumber());
            return new ResponseEntity<>("Failed to update entry", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        try{
            SqlParameterSource param = new MapSqlParameterSource()
            .addValue("firstname", pho.getfirstname())
            .addValue("middleinitial", pho.getmidint())
            .addValue("lastname", pho.getlastname())
            .addValue("phonenumber", formatPhoneNumber(pho.getphonenumber()))
            .addValue("localaddress", pho.getaddress())
            .addValue("email", pho.getemail())
            .addValue("id", pho.getid());
            template.update(sql,param);
            return new ResponseEntity<>("Entry updated successfully", HttpStatus.OK);
        }catch (Exception e) {
            System.out.println("An exception occurred while updating entry: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>("Failed to update entry", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public void deleteentry(phoneentry pho) {
        
        final String sql = "delete from phone where id=:id";
        
        Map<String, Object> paramMap = Collections.singletonMap("id", pho.getid());
        if (paramMap != null) {
            template.execute(sql, paramMap, ps -> ps.executeUpdate());
        } else {
            throw new IllegalArgumentException("Parameter map is null");
        }

    }



    public static boolean validateemail(String email) {

        if(email == null)
            return true;
    
        Matcher matcher = email_regex.matcher(email);
        

        if(email.isEmpty())
        {
            return true;
        }

        else
            return matcher.matches();
    }

    //This functions formats the phonenumber to have "."
    //Input is assumed to be a 10 digit number with no dashes
    public static String formatPhoneNumber(String phoneNumber) {

        
        if(phoneNumber == "" || phoneNumber == null)
        return "";
        
        String first = phoneNumber.substring(0, 3);
        String second = phoneNumber.substring(3, 6);
        String third = phoneNumber.substring(6);

        return first + "." + second + "." + third;
    }


    public static boolean validatephonum(String num) {

        //We allow for an empty string or null to be inserted into database
        //So if the phone number is that value return true which causes the check
        //in insert/update entry to not pass(!return value) 
        if(num == null || num == "")
            return true;

        Matcher matcher = phone_regex.matcher(num);

        return (matcher.matches()) ;
    }

    public boolean checkMidInt(String mid) {
        // Regular expression to match for one letter
        String oneletter = "^[a-zA-Z]$";
        return mid == null || mid.isEmpty() || mid.matches(oneletter);
    }

    public boolean isValidName(String name) {
        // Regular expression to match only letters
        String letteronly = "^[a-zA-Z]*$";
        return name == null || name.matches(letteronly);
    }


}


