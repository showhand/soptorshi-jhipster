import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import { EmployeeComponent, EmployeeDetailComponent, EmployeeUpdateComponent, employeeRoute, employeePopupRoute } from './';
import { EmployeeManagementComponent } from 'app/entities/employee/employee-management.component';

const ENTITY_STATES = [...employeeRoute, ...employeePopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /* declarations: [
        EmployeeComponent,
        EmployeeDetailComponent,
        EmployeeUpdateComponent,
        EmployeeDeleteDialogComponent,
        EmployeeDeletePopupComponent,
        EmployeeManagementComponent
    ],
    entryComponents: [
        EmployeeComponent,
        EmployeeUpdateComponent,
        EmployeeDeleteDialogComponent,
        EmployeeDeletePopupComponent,
        EmployeeManagementComponent
    ],
    exports: [
        EmployeeComponent,
        EmployeeUpdateComponent,
        EmployeeDeleteDialogComponent,
        EmployeeDeletePopupComponent,
        EmployeeManagementComponent
    ],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiEmployeeModule {}
