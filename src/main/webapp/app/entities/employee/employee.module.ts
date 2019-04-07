import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    EmployeeComponent,
    EmployeeDetailComponent,
    EmployeeUpdateComponent,
    EmployeeDeletePopupComponent,
    EmployeeDeleteDialogComponent,
    employeeRoute,
    employeePopupRoute
} from './';
import { EmployeeManagementComponent } from './employee-management.component';
import { MatTabsModule } from '@angular/material';
import { SoptorshiAcademicInformationModule } from 'app/entities/academic-information/academic-information.module';
import { SoptorshiExperienceInformationModule } from 'app/entities/experience-information/experience-information.module';

const ENTITY_STATES = [...employeeRoute, ...employeePopupRoute];

@NgModule({
    imports: [
        SoptorshiSharedModule,
        MatTabsModule,
        SoptorshiAcademicInformationModule,
        SoptorshiExperienceInformationModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EmployeeComponent,
        EmployeeDetailComponent,
        EmployeeUpdateComponent,
        EmployeeDeleteDialogComponent,
        EmployeeDeletePopupComponent,
        EmployeeManagementComponent
    ],
    entryComponents: [EmployeeComponent, EmployeeUpdateComponent, EmployeeDeleteDialogComponent, EmployeeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiEmployeeModule {}
