import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'genericFilter'
})
export class GenericFilter implements PipeTransform {
    transform(value: Object[], objectPropertyName: string, objectPropertyValue: any): any {
        return value.filter(val => val[objectPropertyName] === objectPropertyValue);
    }
}
