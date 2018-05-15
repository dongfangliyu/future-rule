/* DataTables Bootstrap 2 integration
 * ©2011-2014 SpryMedia Ltd - datatables.net/license
 */
(function (A, B, E, D, C) {
    E.extend(true, D.defaults, {
        "dom": "<'row-fluid'<'span4'l><'span8'p>r><'row-fluid'<'span12't>><'row-fluid'<'span4'i><'span8'p>>",
        renderer: "bootstrap"
    });
    E.extend(D.ext.classes, {sWrapper: "dataTables_wrapper form-inline dt-bootstrap"});
    D.ext.renderer.pageButton.bootstrap = function (G, P, M, R, K, N) {
        var Q = new D.Api(G);
        var L = G.oClasses;
        var J = G.oLanguage.oPaginate;
        var I, F;
        var O = function (W, Y) {
            var U, T, V, X;
            var Z = function (a) {
                a.preventDefault();
                if (!E(a.currentTarget).hasClass("disabled")) {
                    Q.page(a.data.action).draw(false)
                }
            };
            for (U = 0, T = Y.length; U < T; U++) {
                X = Y[U];
                if (E.isArray(X)) {
                    O(W, X)
                } else {
                    I = "";
                    F = "";
                    switch (X) {
                        case"ellipsis":
                            I = "&hellip;";
                            F = "disabled";
                            break;
                        case"first":
                            I = J.sFirst;
                            F = X + (K > 0 ? "" : " disabled");
                            break;
                        case"previous":
                            I = J.sPrevious;
                            F = X + (K > 0 ? "" : " disabled");
                            break;
                        case"next":
                            I = J.sNext;
                            F = X + (K < N - 1 ? "" : " disabled");
                            break;
                        case"last":
                            I = J.sLast;
                            F = X + (K < N - 1 ? "" : " disabled");
                            break;
                        default:
                            I = X + 1;
                            F = K === X ? "active" : "";
                            break
                    }
                    if (I) {
                        V = E("<li>", {
                            "class": L.sPageButton + " " + F,
                            "aria-controls": G.sTableId,
                            "tabindex": G.iTabIndex,
                            "id": M === 0 && typeof X === "string" ? G.sTableId + "_" + X : null
                        }).append(E("<a>", {"href": "#"}).html(I)).appendTo(W);
                        G.oApi._fnBindAction(V, {action: X}, Z)
                    }
                }
            }
        };
        O(E(P).empty().html('<div class="pagination"><ul/></div>').find("ul"), R);
        var S = E("<input>", {"type": "number", "min": 1, "max": N}).on("keyup", function (U) {
            if (U.keyCode == 13) {
                var V = this.value.replace(/\s|\D/g, "") | 0;
                if (V) {
                    var T = Q.page.info().pages;
                    V = V > T ? T : V;
                    V--;
                    Q.page(V).draw(false)
                }
            }
        });
        var H = E("<button />", {
            "class": "btn",
            "aria-controls": G.sTableId,
            "tabindex": G.iTabIndex
        }).html(J.sJump).on("click", function () {
            var U = S.val().replace(/\s|\D/g, "") | 0;
            if (U) {
                var T = Q.page.info().pages;
                U = U > T ? T : U;
                U--;
                Q.page(U).draw(false)
            }
        });
        E(P).prepend(E("<div />", {"class": "page_jump input-append"}).append(S).append(H))
    };
    if (D.TableTools) {
        E.extend(true, D.TableTools.classes, {
            "container": "DTTT btn-group",
            "buttons": {"normal": "btn", "disabled": "disabled"},
            "collection": {
                "container": "DTTT_dropdown dropdown-menu",
                "buttons": {"normal": "", "disabled": "disabled"}
            },
            "print": {"info": "DTTT_print_info modal"},
            "select": {"row": "active"}
        });
        E.extend(true, D.TableTools.DEFAULTS.oTags, {"collection": {"container": "ul", "button": "li", "liner": "a"}})
    }
})(window, document, jQuery, jQuery.fn.dataTable);