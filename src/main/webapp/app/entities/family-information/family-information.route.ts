import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { IUser, UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FamilyInformation } from 'app/shared/model/family-information.model';
import { FamilyInformationService } from './family-information.service';
import { FamilyInformationComponent } from './family-information.component';
import { FamilyInformationDetailComponent } from './family-information-detail.component';
import { FamilyInformationUpdateComponent } from './family-information-update.component';
import { FamilyInformationDeletePopupComponent } from './family-information-delete-dialog.component';
import { IFamilyInformation } from 'app/shared/model/family-information.model';

@Injectable({ providedIn: 'root' })
export class FamilyInformationResolve implements Resolve<IFamilyInformation> {
    constructor(private service: FamilyInformationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFamilyInformation> {
        const id = route.params['id'] ? route.params['id'] : null;
        const employeeId = route.params['employeeId'] ? route.params['employeeId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<FamilyInformation>) => response.ok),
                map((familyInformation: HttpResponse<FamilyInformation>) => familyInformation.body)
            );
        } else if (employeeId) {
            const familyInformation: IFamilyInformation = new FamilyInformation();
            familyInformation.employeeId = employeeId;
            return of(familyInformation);
        }
        return of(new FamilyInformation());
    }
}

export const familyInformationRoute: Routes = [
    {
        path: '',
        component: FamilyInformationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'FamilyInformations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: FamilyInformationDetailComponent,
        resolve: {
            familyInformation: FamilyInformationResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'FamilyInformations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: FamilyInformationUpdateComponent,
        resolve: {
            familyInformation: FamilyInformationResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'FamilyInformations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':employeeId/new',
        component: FamilyInformationUpdateComponent,
        resolve: {
            familyInformation: FamilyInformationResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'FamilyInformations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: FamilyInformationUpdateComponent,
        resolve: {
            familyInformation: FamilyInformationResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'FamilyInformations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const familyInformationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: FamilyInformationDeletePopupComponent,
        resolve: {
            familyInformation: FamilyInformationResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'FamilyInformations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
