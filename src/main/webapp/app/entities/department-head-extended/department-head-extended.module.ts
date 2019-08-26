import {
    DepartmentHeadComponent,
    DepartmentHeadDeleteDialogComponent,
    DepartmentHeadDeletePopupComponent,
    DepartmentHeadDetailComponent,
    departmentHeadPopupRoute,
    departmentHeadRoute,
    DepartmentHeadUpdateComponent
} from 'app/entities/department-head';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { SoptorshiSharedModule } from 'app/shared';
import { RouterModule } from '@angular/router';
import { DepartmentHeadExtendedComponent } from 'app/entities/department-head-extended/department-head-extended.component';
import { DepartmentHeadExtendedDetailsComponent } from 'app/entities/department-head-extended/department-head-extended-details.component';
import { DepartmentHeadExtendedUpdateComponent } from 'app/entities/department-head-extended/department-head-extended-update.component';
import { departmentHeadExtendedRoute } from 'app/entities/department-head-extended/department-head-extended.route';
import {
    DepartmentHeadExtendedDeleteDialogComponent,
    DepartmentHeadExtendedDeletePopupComponent
} from 'app/entities/department-head-extended/department-head-extended-delete-dialog.component';

const ENTITY_STATES = [...departmentHeadExtendedRoute, ...departmentHeadExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DepartmentHeadComponent,
        DepartmentHeadDetailComponent,
        DepartmentHeadUpdateComponent,
        DepartmentHeadExtendedComponent,
        DepartmentHeadExtendedDetailsComponent,
        DepartmentHeadExtendedUpdateComponent,
        DepartmentHeadDeleteDialogComponent,
        DepartmentHeadDeletePopupComponent,
        DepartmentHeadExtendedDeletePopupComponent,
        DepartmentHeadExtendedDeleteDialogComponent
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
