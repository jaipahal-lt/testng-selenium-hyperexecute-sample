import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Self-contained stand-in for https://lambdatest.github.io/sample-todo-app/ (the GitHub
 * Pages site is gone). Same DOM contract the tests drive: #sampletodotext + Enter adds an
 * item, five seeded items with checkboxes, and a .ng-binding counter reading
 * "N of M tasks remaining". Served as a data: URL so no host has to reach a website.
 */
public final class TodoApp {
    private TodoApp() {}

    private static final String HTML = ""
        + "<!doctype html><html><head><meta charset='utf-8'>"
        + "<title>Sample page - lambdatest.com</title></head><body>"
        + "<h1>LambdaTest Sample App</h1>"
        + "<span class='ng-binding' id='remaining'>5 of 5 tasks remaining</span>"
        + "<ul id='list'>"
        + "<li class='ng-scope'><input type='checkbox' id='li1'> <span>First Item</span></li>"
        + "<li class='ng-scope'><input type='checkbox' id='li2'> <span>Second Item</span></li>"
        + "<li class='ng-scope'><input type='checkbox' id='li3'> <span>Third Item</span></li>"
        + "<li class='ng-scope'><input type='checkbox' id='li4'> <span>Fourth Item</span></li>"
        + "<li class='ng-scope'><input type='checkbox' id='li5'> <span>Fifth Item</span></li>"
        + "</ul>"
        + "<form id='form'><input id='sampletodotext' placeholder='Want to add more?'>"
        + "<input id='addbutton' type='submit' value='add'></form>"
        + "<script>"
        + "var list=document.getElementById('list');"
        + "function refresh(){var boxes=list.querySelectorAll('input[type=checkbox]');var done=0;"
        + "for(var i=0;i<boxes.length;i++){if(boxes[i].checked)done++;}"
        + "document.getElementById('remaining').textContent=(boxes.length-done)+' of '+boxes.length+' tasks remaining';}"
        + "list.addEventListener('change',refresh);"
        + "document.getElementById('form').addEventListener('submit',function(e){e.preventDefault();"
        + "var t=document.getElementById('sampletodotext');if(!t.value)return;"
        + "var li=document.createElement('li');li.className='ng-scope';"
        + "var n=list.children.length+1;"
        + "li.innerHTML=\"<input type='checkbox' id='li\"+n+\"'> <span></span>\";"
        + "li.querySelector('span').textContent=t.value;list.appendChild(li);t.value='';refresh();});"
        + "</script></body></html>";

    public static String url() {
        try {
            return "data:text/html;charset=utf-8," + URLEncoder.encode(HTML, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }
}
