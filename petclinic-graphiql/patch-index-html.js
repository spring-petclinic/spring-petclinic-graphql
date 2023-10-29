import fs from "fs";

const file = process.argv[2];

console.log("patching index file", file);
const data = fs.readFileSync(file, "utf-8");

const newData = data.replaceAll("__BACKEND__", "");

fs.writeFileSync(file, newData);
