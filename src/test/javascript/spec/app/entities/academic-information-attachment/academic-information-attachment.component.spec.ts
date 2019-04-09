/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SoptorshiTestModule } from '../../../test.module';
import { AcademicInformationAttachmentComponent } from 'app/entities/academic-information-attachment/academic-information-attachment.component';
import { AcademicInformationAttachmentService } from 'app/entities/academic-information-attachment/academic-information-attachment.service';
import { AcademicInformationAttachment } from 'app/shared/model/academic-information-attachment.model';

describe('Component Tests', () => {
    describe('AcademicInformationAttachment Management Component', () => {
        let comp: AcademicInformationAttachmentComponent;
        let fixture: ComponentFixture<AcademicInformationAttachmentComponent>;
        let service: AcademicInformationAttachmentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [AcademicInformationAttachmentComponent],
                providers: []
            })
                .overrideTemplate(AcademicInformationAttachmentComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AcademicInformationAttachmentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AcademicInformationAttachmentService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AcademicInformationAttachment(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.academicInformationAttachments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
