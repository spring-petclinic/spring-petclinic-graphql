/**
 * Place this file in your Reveal.js presentation in 'lib/js/'.
 *
 * Add this javascript library to a reaveal.js presentation by adding it as a
 * dependency in Reveal.initialize.
 *
 * Reveal.initialize({
 *   ....
 *   dependencies: [
 *     ...
 *     {src: 'lib/js/line-numbers.js'}
 *   ]
 * })
 *
 */

// Adding an event listener on slidechanged event to add line numbers to
// code blocks.
Reveal.addEventListener('slidechanged', function(event) {
    // For any code blocks in the current slide with class 'line-numbers'.
    $('code.line-numbers').each(function(){
        // Check ihighlight js has already run and not already added line numbers
        if ($(this).hasClass('hljs') && ($(this).html().indexOf('class="line-number') < 1)) {
            var leftpad = $(this).attr('data-leftpad') !== undefined;
            var numStart = Number($(this).attr('data-num-start')) || 1;
            // Get the content of the code block.
            var content = $(this).html();
            var lines = content.split("\n");
            var linesWithNumbers = lines.map(function (line, index) {
                var lineNumber = index + numStart;
                var lineNumberString = String(lineNumber);
                // leftpad for poor people
                if ((lines.length > 9 || leftpad) && lineNumberString.length === 1) {
                    lineNumberString = ' ' + lineNumberString;
                }
                var lineWithNumbers = '<span class="line-number">' + lineNumberString + '  </span>' + line;
                return lineWithNumbers;
            });
            var newContent = linesWithNumbers.join('\n');
            $(this).html(newContent);
        }
    });
});

