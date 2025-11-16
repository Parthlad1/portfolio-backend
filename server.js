// ==========================
// Contact Form Backend
// ==========================
const express = require("express");
const nodemailer = require("nodemailer");
const cors = require("cors");
require("dotenv").config();

const app = express();
app.use(express.json());
app.use(cors());

// ==========================
// TEST ROUTE
// ==========================
app.get("/", (req, res) => {
  res.send("✅ Backend is running on localhost:5000!");
});

// ==========================
// CONTACT ROUTE
// ==========================
app.post("/contact", async (req, res) => {
    console.log("POST /contact hit");
    console.log(req.body);
  const { name, email, message } = req.body;

  if (!name || !email || !message) {
    return res.status(400).json({ message: "All fields are required" });
  }

  try {
    const transporter = nodemailer.createTransport({
      service: "gmail",
      auth: {
        user: process.env.EMAIL_USER,
        pass: process.env.EMAIL_PASS,
      },
    });

    const mailOptions = {
      from: `"Portfolio Contact" <${process.env.EMAIL_USER}>`,
      to: process.env.EMAIL_USER,
      subject: `New Contact Form Message from ${name}`,
      text: `
You received a new contact form submission:

Name: ${name}
Email: ${email}
Message: ${message}
      `,
    };

    await transporter.sendMail(mailOptions);

    res.json({ message: "Your message has been sent successfully!" });
  } catch (error) {
    console.error("Error sending email:", error);
    res.status(500).json({ message: "Failed to send message. Please try again." });
  }
});

// ==========================
// SERVER START
// ==========================
const PORT = process.env.PORT || 5000;
app.listen(PORT, () => console.log(`Backend running on port ${PORT}`));
