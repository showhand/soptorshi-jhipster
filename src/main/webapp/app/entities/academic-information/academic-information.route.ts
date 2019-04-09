import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AcademicInformation } from 'app/shared/model/academic-information.model';
import { AcademicInformationService } from './academic-information.service';
import { AcademicInformationComponent } from './academic-information.component';
import { AcademicInformationDetailComponent } from './academic-information-detail.component';
import { AcademicInformationUpdateComponent } from './academic-information-update.component';
import { AcademicInformationDeletePopupComponent } from './academic-information-delete-dialog.component';
import { IAcademicInformation } from 'app/shared/model/academic-information.model';

@Injectable({ providedIn: 'root' })
export class AcademicInformationResolve implements Resolve<IAcademicInformation> {
    constructor(private service: AcademicInformationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAcademicInformation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AcademicInformation>) => response.ok),
                map((academicInformation: HttpResponse<AcademicInformation>) => academicInformation.body)
            );
        }
        return of(new AcademicInformation());
    }
}

export const academicInformationRoute: Routes = [
    {
        path: '',
        component: AcademicInformationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'AcademicInformations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AcademicInformationDetailComponent,
        resolve: {
            academicInformation: AcademicInformationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AcademicInformations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AcademicInformationUpdateComponent,
        resolve: {
            academicInformation: AcademicInformationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AcademicInformations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AcademicInformationUpdateComponent,
        resolve: {
            academicInformation: AcademicInformationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AcademicInformations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const academicInformationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AcademicInformationDeletePopupComponent,
        resolve: {
            academicInformation: AcademicInformationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AcademicInformations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
