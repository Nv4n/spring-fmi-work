module.exports = {
	darkMode: "class",
	content: [
		"./views/**/*{.go.html,.html,.templ}",
		"./routes/**/*.go",
		"./node_modules/preline/dist/*.js",
	],
	theme: {
		extend: {},
	},
	plugins: [
		require("tailwindcss-animate"),
		require("@tailwindcss/forms"),
		require("preline/plugin"),
	],
};
