import express from "express";
import dotenv from "dotenv";


dotenv.config();


const app = express();

app.use(express.json());

const PORT=process.env.port ||3000;


app.get("/",(req,res)=>{
res.json({
message:"server is running"
});


app.listen(PORT,()=>{
console.log(`server is running on port ${PORT}`);
});


