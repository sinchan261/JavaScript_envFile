package com.example.FileGenerator.EnvFile.Configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SupabaseConfiguration {
    @Value("${supabase.url}")
    private  String supabaseUrl;
    @Value("${supabase.key}")
    private String supabaseKey;
    @Value("${supabase.bucket}")
    private String supabaseBucket;

    public String getSupabaseUrl() {
        return supabaseUrl;
    }

    public void setSupabaseUrl(String supabaseUrl) {
        this.supabaseUrl = supabaseUrl;
    }

    public String getSupabaseKey() {
        return supabaseKey;
    }

    public void setSupabaseKey(String supabaseKey) {
        this.supabaseKey = supabaseKey;
    }

    public String getSupabaseBucket() {
        return supabaseBucket;
    }

    public void setSupabaseBucket(String supabaseBucket) {
        this.supabaseBucket = supabaseBucket;
    }
}
