import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatTabsModule } from '@angular/material';

@NgModule({
    imports: [
        MatTabsModule,
        RouterModule.forChild([
            {
                path: 'department',
                loadChildren: './department/department.module#SoptorshiDepartmentModule'
            },
            {
                path: 'designation',
                loadChildren: './designation/designation.module#SoptorshiDesignationModule'
            },
            {
                path: 'employee',
                loadChildren: './employee/employee.module#SoptorshiEmployeeModule'
            },
            {
                path: 'attachment',
                loadChildren: './attachment/attachment.module#SoptorshiAttachmentModule'
            },
            {
                path: 'academic-information',
                loadChildren: './academic-information/academic-information.module#SoptorshiAcademicInformationModule'
            },
            {
                path: 'training-information',
                loadChildren: './training-information/training-information.module#SoptorshiTrainingInformationModule'
            },
            {
                path: 'family-information',
                loadChildren: './family-information/family-information.module#SoptorshiFamilyInformationModule'
            },
            {
                path: 'reference-information',
                loadChildren: './reference-information/reference-information.module#SoptorshiReferenceInformationModule'
            },
            {
                path: 'experience-information',
                loadChildren: './experience-information/experience-information.module#SoptorshiExperienceInformationModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiEntityModule {}
