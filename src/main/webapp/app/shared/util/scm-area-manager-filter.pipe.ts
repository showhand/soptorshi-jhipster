import { Pipe, PipeTransform } from '@angular/core';
import { SupplyAreaManager } from 'app/shared/model/supply-area-manager.model';

@Pipe({ name: 'scmAreaManagerFilter' })
export class ScmAreaManagerFilterPipe implements PipeTransform {
    transform(value: number, args: SupplyAreaManager[]): string {
        return args.find(areaManager => areaManager.id == value).employeeFullName;
    }
}
