import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    EmployeeExtendedComponent,
    EmployeeExtendedDetailComponent,
    EmployeeExtendedUpdateComponent,
    employeeExtendedRoute,
    employeePopupRoute
} from './';
import { EmployeeManagementComponent } from 'app/entities/employee/employee-management.component';
import { EmployeeComponent, EmployeeDetailComponent, EmployeeUpdateComponent } from 'app/entities/employee';
import { EmployeeDeleteDialogComponent, EmployeeDeletePopupComponent } from 'app/entities/employee/employee-delete-dialog.component';

const ENTITY_STATES = [...employeeExtendedRoute, ...employeePopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EmployeeComponent,
        EmployeeDetailComponent,
        EmployeeUpdateComponent,
        EmployeeExtendedComponent,
        EmployeeExtendedDetailComponent,
        EmployeeExtendedUpdateComponent,
        EmployeeDeleteDialogComponent,
        EmployeeDeletePopupComponent,
        EmployeeManagementComponent
    ],
    entryComponents: [
        EmployeeExtendedComponent,
        EmployeeExtendedUpdateComponent,
        EmployeeDeleteDialogComponent,
        EmployeeDeletePopupComponent,
        EmployeeManagementComponent
    ],
    exports: [EmployeeExtendedComponent, EmployeeExtendedUpdateComponent, EmployeeManagementComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiEmployeeModule {}
