/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { AcademicInformationAttachmentDetailComponent } from 'app/entities/academic-information-attachment/academic-information-attachment-detail.component';
import { AcademicInformationAttachment } from 'app/shared/model/academic-information-attachment.model';

describe('Component Tests', () => {
    describe('AcademicInformationAttachment Management Detail Component', () => {
        let comp: AcademicInformationAttachmentDetailComponent;
        let fixture: ComponentFixture<AcademicInformationAttachmentDetailComponent>;
        const route = ({ data: of({ academicInformationAttachment: new AcademicInformationAttachment(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [AcademicInformationAttachmentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AcademicInformationAttachmentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AcademicInformationAttachmentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.academicInformationAttachment).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
