import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ExperienceInformationAttachment } from 'app/shared/model/experience-information-attachment.model';
import { ExperienceInformationAttachmentService } from './experience-information-attachment.service';
import { ExperienceInformationAttachmentComponent } from './experience-information-attachment.component';
import { ExperienceInformationAttachmentDetailComponent } from './experience-information-attachment-detail.component';
import { ExperienceInformationAttachmentUpdateComponent } from './experience-information-attachment-update.component';
import { ExperienceInformationAttachmentDeletePopupComponent } from './experience-information-attachment-delete-dialog.component';
import { IExperienceInformationAttachment } from 'app/shared/model/experience-information-attachment.model';

@Injectable({ providedIn: 'root' })
export class ExperienceInformationAttachmentResolve implements Resolve<IExperienceInformationAttachment> {
    constructor(private service: ExperienceInformationAttachmentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IExperienceInformationAttachment> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ExperienceInformationAttachment>) => response.ok),
                map(
                    (experienceInformationAttachment: HttpResponse<ExperienceInformationAttachment>) => experienceInformationAttachment.body
                )
            );
        }
        return of(new ExperienceInformationAttachment());
    }
}

export const experienceInformationAttachmentRoute: Routes = [];

export const experienceInformationAttachmentPopupRoute: Routes = [];
