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
import { SoptorshiFamilyInformationModule } from 'app/entities/family-information/family-information.module';
import { SoptorshiTrainingInformationModule } from 'app/entities/training-information/training-information.module';
import { SoptorshiReferenceInformationModule } from 'app/entities/reference-information/reference-information.module';
import { SoptorshiAcademicInformationAttachmentModule } from 'app/entities/academic-information-attachment/academic-information-attachment.module';
import { SoptorshiExperienceInformationAttachmentModule } from 'app/entities/experience-information-attachment/experience-information-attachment.module';

const ENTITY_STATES = [...employeeRoute, ...employeePopupRoute];

@NgModule({
    imports: [
        SoptorshiSharedModule,
        MatTabsModule,
        SoptorshiAcademicInformationModule,
        SoptorshiExperienceInformationModule,
        SoptorshiFamilyInformationModule,
        SoptorshiTrainingInformationModule,
        SoptorshiReferenceInformationModule,
        RouterModule.forChild(ENTITY_STATES),
        SoptorshiAcademicInformationAttachmentModule,
        SoptorshiExperienceInformationAttachmentModule
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
