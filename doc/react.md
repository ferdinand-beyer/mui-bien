# React

Raw notes on reagent-react interop.

[Reagent React Features][react-feat]

Reagent by default creates _class components_ from Hiccup.

React hooks only work with _functional components_.
`create-element` creates functional components from Clojure functions,
but need to use `as-element` inside to create markup.

* `as-element`: Hiccup vector -> React element (virtual dom)
* `create-class`: Construct a JS React class component from a map of functions,
  e.g. to implement lifecycle handlers.  Need to define a render function,
  supports `:reagent-render` for Hiccup support.
* `create-element`: Create a JS React element, like [`React.createElement`][create-element],
  needs JS props + elements.
* `:r>`: Shortcut related to `create-element` (TODO: Usage example)
* `:f>`: Shortcut to create function components from Reagent components,
  supports hooks and atoms
* Componens can be compiled to function components by default when using
  a custom [Reagent Compiler][reagent-compiler].
* Make a JS function (expecting JS args) a Reagent element:
  * `adapt-react-class`: React JS class -> Reagent component
  * `:>`
* `reactify-component`: Takes `(fn [props]) -> hiccup` and creates a JS
  component to be passed to JS react components

## Hooks

```clojure
(defn example []
  (let [[count set-count] (react/useState 0)]
    [:div
     [:p "You clicked " count " times"]
     [:button
      {:on-click #(set-count inc)}
      "Click"]])))

(defn root []
 [:div
  [:f> example]])
```

## Helpers

* `merge-props` - before passing them down?
* `with-let` - let, but evaluate only once.  Instead of inner fn?

[react-feat]: https://github.com/reagent-project/reagent/blob/master/doc/ReactFeatures.md
[reagent-compiler]: https://github.com/reagent-project/reagent/blob/master/doc/ReagentCompiler.md
[create-element]: https://reactjs.org/docs/react-api.html#createelement