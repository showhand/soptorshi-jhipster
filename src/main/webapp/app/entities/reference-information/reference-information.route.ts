import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ReferenceInformation } from 'app/shared/model/reference-information.model';
import { ReferenceInformationService } from './reference-information.service';
import { ReferenceInformationComponent } from './reference-information.component';
import { ReferenceInformationDetailComponent } from './reference-information-detail.component';
import { ReferenceInformationUpdateComponent } from './reference-information-update.component';
import { ReferenceInformationDeletePopupComponent } from './reference-information-delete-dialog.component';
import { IReferenceInformation } from 'app/shared/model/reference-information.model';

@Injectable({ providedIn: 'root' })
export class ReferenceInformationResolve implements Resolve<IReferenceInformation> {
    constructor(private service: ReferenceInformationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IReferenceInformation> {
        const id = route.params['id'] ? route.params['id'] : null;
        const employeeId = route.params['employeeId'] ? route.params['employeeId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ReferenceInformation>) => response.ok),
                map((referenceInformation: HttpResponse<ReferenceInformation>) => referenceInformation.body)
            );
        } else if (employeeId) {
            const referenceInformation: IReferenceInformation = new ReferenceInformation();
            referenceInformation.employeeId = employeeId;
            return of(referenceInformation);
        }
        return of(new ReferenceInformation());
    }
}

export const referenceInformationRoute: Routes = [
    {
        path: 'home',
        component: ReferenceInformationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_EMPLOYEE_MANAGEMENT'],
            defaultSort: 'id,asc',
            pageTitle: 'ReferenceInformations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ReferenceInformationDetailComponent,
        resolve: {
            referenceInformation: ReferenceInformationResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_EMPLOYEE_MANAGEMENT'],
            pageTitle: 'ReferenceInformations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ReferenceInformationUpdateComponent,
        resolve: {
            referenceInformation: ReferenceInformationResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_EMPLOYEE_MANAGEMENT'],
            pageTitle: 'ReferenceInformations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':employeeId/new',
        component: ReferenceInformationUpdateComponent,
        resolve: {
            referenceInformation: ReferenceInformationResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_EMPLOYEE_MANAGEMENT'],
            pageTitle: 'ReferenceInformations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ReferenceInformationUpdateComponent,
        resolve: {
            referenceInformation: ReferenceInformationResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_EMPLOYEE_MANAGEMENT'],
            pageTitle: 'ReferenceInformations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const referenceInformationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ReferenceInformationDeletePopupComponent,
        resolve: {
            referenceInformation: ReferenceInformationResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_EMPLOYEE_MANAGEMENT'],
            pageTitle: 'ReferenceInformations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
