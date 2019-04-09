/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { ExperienceInformationAttachmentDetailComponent } from 'app/entities/experience-information-attachment/experience-information-attachment-detail.component';
import { ExperienceInformationAttachment } from 'app/shared/model/experience-information-attachment.model';

describe('Component Tests', () => {
    describe('ExperienceInformationAttachment Management Detail Component', () => {
        let comp: ExperienceInformationAttachmentDetailComponent;
        let fixture: ComponentFixture<ExperienceInformationAttachmentDetailComponent>;
        const route = ({
            data: of({ experienceInformationAttachment: new ExperienceInformationAttachment(123) })
        } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ExperienceInformationAttachmentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ExperienceInformationAttachmentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExperienceInformationAttachmentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.experienceInformationAttachment).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
