import {
    DepartmentHeadDeleteDialogComponent,
    DepartmentHeadDeletePopupComponent,
    departmentHeadPopupRoute,
    departmentHeadRoute
} from 'app/entities/department-head';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { SoptorshiSharedModule } from 'app/shared';
import { RouterModule } from '@angular/router';
import { DepartmentHeadExtendedComponent } from 'app/entities/department-head-extended/department-head-extended.component';
import { DepartmentHeadExtendedDetailsComponent } from 'app/entities/department-head-extended/department-head-extended-details.component';
import { DepartmentHeadExtendedUpdateComponent } from 'app/entities/department-head-extended/department-head-extended-update.component';
import { departmentHeadExtendedRoute } from 'app/entities/department-head-extended/department-head-extended.route';

const ENTITY_STATES = [...departmentHeadExtendedRoute, ...departmentHeadExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DepartmentHeadExtendedComponent,
        DepartmentHeadExtendedDetailsComponent,
        DepartmentHeadExtendedUpdateComponent,
        DepartmentHeadDeleteDialogComponent,
        DepartmentHeadDeletePopupComponent
    ],
    entryComponents: [
        DepartmentHeadExtendedComponent,
        DepartmentHeadExtendedDetailsComponent,
        DepartmentHeadExtendedUpdateComponent,
        DepartmentHeadDeleteDialogComponent,
        DepartmentHeadDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiDepartmentHeadExtendedModule {}
