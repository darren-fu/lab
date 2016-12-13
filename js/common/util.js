/**
 * Created by darrenfu on 2016/8/19.
 */

/**
 * Debounce a function so it only gets called after the
 * input stops arriving after the given wait period.
 *
 * @param {Function} func
 * @param {Number} wait
 * @return {Function} - the debounced function
 */

exports.debounce = function (func, wait) {
    var timeout, args, context, timestamp, result;
    var later = function later() {
        var last = Date.now() - timestamp;
        if (last < wait && last >= 0) {
            timeout = setTimeout(later, wait - last);
        } else {
            timeout = null;
            result = func.apply(context, args);
            if (!timeout) context = args = null;
        }
    };
    return function () {
        context = this;
        args = arguments;
        timestamp = Date.now();
        if (!timeout) {
            timeout = setTimeout(later, wait);
        }
        return result;
    };
}

exports.indexOf = function (arr, obj) {
    var i = arr.length;
    while (i--) {
        if (arr[i] === obj) return i;
    }
    return -1;
}
/**
 * Mix properties into target object.
 *
 * @param {Object} to
 * @param {Object} from
 */

exports.extend = function (to, from) {
    var keys = Object.keys(from);
    var i = keys.length;
    while (i--) {
        to[keys[i]] = from[keys[i]];
    }
    return to;
}

exports.toNumber = function (value) {
    if (typeof value !== 'string') {
        return value;
    } else {
        var parsed = Number(value);
        return isNaN(parsed) ? value : parsed;
    }
}

