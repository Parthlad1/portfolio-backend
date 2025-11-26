// ==========================
// Contact Form Backend (SendGrid Version)
// ==========================
const express = require("express");
const cors = require("cors");
const sgMail = require("@sendgrid/mail");
require("dotenv").config();

const app = express();
app.use(express.json());
app.use(cors());

// Set SendGrid API Key
sgMail.setApiKey(process.env.SENDGRID_API_KEY);

// ==========================
// TEST ROUTE
// ==========================
app.get("/", (req, res) => {
  res.send("✅ Backend is running with SendGrid!");
});

// ==========================
// CONTACT ROUTE
// ==========================
app.post("/contact", async (req, res) => {
  console.log("Received contact form submission:");
  const { name, email, message } = req.body;

  if (!name || !email || !message) {
    return res.status(400).json({ message: "All fields are required" });
  }

  const msg = {
    to: process.env.EMAIL_TO,      // receiver email (your email)
    from: process.env.EMAIL_FROM,  // verified sender email from SendGrid
    subject: `New Message from ${name}`,
    text: `
You received a new contact form submission:

Name: ${name}
Email: ${email}
Message: ${message}
`,
  };

  // 🔥 Send response immediately (no waiting)
  res.json({ message: "Your message is being processed..." });

  // 📨 Send email in the background
  sgMail.send(msg).catch((err) => console.error("SendGrid Error:", err));
});

// ==========================
// SERVER START
// ==========================
const PORT = process.env.PORT || 5000;
app.listen(PORT, () => console.log(`Backend running on port ${PORT}`));
