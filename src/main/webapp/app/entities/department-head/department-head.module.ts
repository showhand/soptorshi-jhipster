import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    DepartmentHeadComponent,
    DepartmentHeadDetailComponent,
    DepartmentHeadUpdateComponent,
    DepartmentHeadDeletePopupComponent,
    DepartmentHeadDeleteDialogComponent,
    departmentHeadRoute,
    departmentHeadPopupRoute
} from './';

const ENTITY_STATES = [...departmentHeadRoute, ...departmentHeadPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /*declarations: [
        DepartmentHeadComponent,
        DepartmentHeadDetailComponent,
        DepartmentHeadUpdateComponent,
        DepartmentHeadDeleteDialogComponent,
        DepartmentHeadDeletePopupComponent
    ],
    entryComponents: [
        DepartmentHeadComponent,
        DepartmentHeadUpdateComponent,
        DepartmentHeadDeleteDialogComponent,
        DepartmentHeadDeletePopupComponent
    ],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiDepartmentHeadModule {}
