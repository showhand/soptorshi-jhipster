import NumberFormat = Intl.NumberFormat;

export class SoptorshiUtil {
    public static writeFileContent(data: any, contentType: string, fileName: string) {
        var file = new Blob([data], { type: contentType });
        var reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onloadend = function(e) {
            SoptorshiUtil.saveAsFile(reader.result, fileName);
        };
    }
    public static saveAsFile(url, fileName) {
        var a: any = document.createElement('a');
        document.body.appendChild(a);
        a.style = 'display: none';
        a.href = url;
        a.download = fileName;
        a.click();
        window.URL.revokeObjectURL(url);
        $(a).remove();
    }
}
