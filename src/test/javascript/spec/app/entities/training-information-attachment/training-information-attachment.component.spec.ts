/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SoptorshiTestModule } from '../../../test.module';
import { TrainingInformationAttachmentComponent } from 'app/entities/training-information-attachment/training-information-attachment.component';
import { TrainingInformationAttachmentService } from 'app/entities/training-information-attachment/training-information-attachment.service';
import { TrainingInformationAttachment } from 'app/shared/model/training-information-attachment.model';

describe('Component Tests', () => {
    describe('TrainingInformationAttachment Management Component', () => {
        let comp: TrainingInformationAttachmentComponent;
        let fixture: ComponentFixture<TrainingInformationAttachmentComponent>;
        let service: TrainingInformationAttachmentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [TrainingInformationAttachmentComponent],
                providers: []
            })
                .overrideTemplate(TrainingInformationAttachmentComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TrainingInformationAttachmentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TrainingInformationAttachmentService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TrainingInformationAttachment(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.trainingInformationAttachments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
