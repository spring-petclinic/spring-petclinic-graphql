import * as React from "react";

export default function Input() {
  return (
    <div className="py-3.5 ">
      <div className="col-span-3 sm:col-span-2">
        <label htmlFor="company_website" className="block font-font-medium">
          Lorem
        </label>
        <div className="mt-1 flex rounded-md shadow-sm ">
          <input
            type="text"
            name="company_website"
            id="company_website"
            className="flex-1 focus:ring-3 hover:border-spr-green focus:border-spr-green focus:ring-spr-green"
          />
        </div>
      </div>
    </div>
  );
}
