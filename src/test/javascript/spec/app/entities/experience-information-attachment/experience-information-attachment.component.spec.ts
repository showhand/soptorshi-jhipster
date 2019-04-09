/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SoptorshiTestModule } from '../../../test.module';
import { ExperienceInformationAttachmentComponent } from 'app/entities/experience-information-attachment/experience-information-attachment.component';
import { ExperienceInformationAttachmentService } from 'app/entities/experience-information-attachment/experience-information-attachment.service';
import { ExperienceInformationAttachment } from 'app/shared/model/experience-information-attachment.model';

describe('Component Tests', () => {
    describe('ExperienceInformationAttachment Management Component', () => {
        let comp: ExperienceInformationAttachmentComponent;
        let fixture: ComponentFixture<ExperienceInformationAttachmentComponent>;
        let service: ExperienceInformationAttachmentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ExperienceInformationAttachmentComponent],
                providers: []
            })
                .overrideTemplate(ExperienceInformationAttachmentComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExperienceInformationAttachmentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExperienceInformationAttachmentService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ExperienceInformationAttachment(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.experienceInformationAttachments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
